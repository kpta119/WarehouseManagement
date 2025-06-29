package com.example.warehouse.services.impl;

import com.example.warehouse.domain.*;
import com.example.warehouse.domain.dto.dateDtos.Period;
import com.example.warehouse.domain.dto.filtersDto.ProductSearchFilterDto;
import com.example.warehouse.domain.dto.productDtos.ProductDataBaseDto;
import com.example.warehouse.domain.dto.productDtos.ProductDetailsResponseDto;
import com.example.warehouse.domain.dto.productDtos.ProductsInventoryDto;
import com.example.warehouse.domain.dto.productDtos.ProductsResponseDto;
import com.example.warehouse.domain.dto.transactionDtos.ProductTransactionInfoDto;
import com.example.warehouse.mappers.ProductMapper;
import com.example.warehouse.repositories.CategoryRepository;
import com.example.warehouse.repositories.ProductInventoryRepository;
import com.example.warehouse.repositories.ProductRepository;
import com.example.warehouse.repositories.TransactionProductRepository;
import com.example.warehouse.services.ProductsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductInventoryRepository productInventoryRepository;
    private final TransactionProductRepository transactionProductRepository;
    private final CategoryRepository categoryRepository;
    private final Clock clock;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ProductsServiceImpl(ProductRepository productRepository, ProductInventoryRepository productInventoryRepository, TransactionProductRepository transactionProductRepository, CategoryRepository categoryRepository, Clock clock, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productInventoryRepository = productInventoryRepository;
        this.transactionProductRepository = transactionProductRepository;
        this.categoryRepository = categoryRepository;
        this.clock = clock;
    }

    @Override
    public Page<ProductsResponseDto> getAllProducts(ProductSearchFilterDto productFilters, Pageable pageable) {
        Page<ProductsResponseDto> productsWithInventory = productRepository.findAllProducts(productFilters, pageable);
        List<Integer> lowStockProductIds = getLowStockProductIds(productFilters.getWarehouseId());
        List<Integer> bestSellingProducts = getBestSellingProducts(productFilters.getWarehouseId(), Period.allTime);
        productsWithInventory.forEach(product -> {
            product.setIsLowStock(lowStockProductIds.contains(product.getProductId()));
            product.setIsBestSelling(bestSellingProducts.contains(product.getProductId()));

        });
        return productsWithInventory;
    }

    @Override
    public ProductDetailsResponseDto getProductDetails(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));
        List<ProductsInventoryDto> inventory = getProductsInventory(product);
        List<ProductTransactionInfoDto> transactions = getTransactionsDto(productId);
        return productMapper.mapToDto(product, inventory, transactions);
    }

    @Override
    public List<ProductsInventoryDto> getProductsInventory(Product product) {
        List<ProductInventory> productInventories = product.getProductInventories();
        return productInventories.stream()
                .map(productInventory -> {
                    Warehouse warehouse = productInventory.getWarehouse();
                    ProductsInventoryDto inventoryDto = new ProductsInventoryDto();

                    inventoryDto.setWarehouseId(warehouse.getId());
                    inventoryDto.setWarehouseName(warehouse.getName());
                    inventoryDto.setQuantity(productInventory.getQuantity());
                    return inventoryDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductTransactionInfoDto> getTransactionsDto(Integer productId) {

        List<TransactionProduct> transactions = transactionProductRepository.findByProductId(productId);
        return transactions.stream().map(transactionProduct -> {
            Employee employee = transactionProduct.getTransaction().getEmployee();
            Transaction transaction = transactionProduct.getTransaction();
            ProductTransactionInfoDto transactionInfoDto = new ProductTransactionInfoDto();

            transactionInfoDto.setTransactionId(transaction.getId());
            transactionInfoDto.setDate(simpleDateFormat.format(transaction.getDate()));
            transactionInfoDto.setType(transaction.getTransactionType().name());
            transactionInfoDto.setPrice(transactionProduct.getTransactionPrice());
            transactionInfoDto.setQuantity(transactionProduct.getQuantity());
            transactionInfoDto.setEmployeeId(employee.getId());
            transactionInfoDto.setEmployeeName(employee.getName() + " " + employee.getSurname());
            return transactionInfoDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Integer> getLowStockProductIds(Integer warehouseId) {
        int lowStockThreshold = 5;
        return (warehouseId != null) ? productInventoryRepository.findLowStockProductsByWarehouseId(warehouseId, lowStockThreshold)
                : productInventoryRepository.findLowStockProducts(lowStockThreshold);
    }


    @Override
    public List<Integer> getBestSellingProducts(Integer warehouseId, Period parsedPeriod) {
        LocalDate now = LocalDate.now(clock);
        Date fromDate = switch (parsedPeriod) {
            case week -> Date.valueOf(now.minusWeeks(1));
            case month -> Date.valueOf(now.minusMonths(1));
            case year -> Date.valueOf(now.minusYears(1));
            case allTime -> Date.valueOf(LocalDate.of(1970, 1, 1));
        };
        int topN = 3;
        PageRequest pageRequest = PageRequest.of(0, topN);
        return (warehouseId != null) ? transactionProductRepository.findTopNBestSellingProductsByWarehouseId(warehouseId, fromDate, pageRequest)
                : transactionProductRepository.findTopNBestSellingProducts(fromDate, pageRequest);
    }

    @Override
    public ProductDataBaseDto createProduct(ProductDataBaseDto productDto) {
        Product product = new Product();
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + productDto.getCategoryId()));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setUnitPrice(productDto.getUnitPrice());
        product.setUnitSize(productDto.getUnitSize());
        product.setCategory(category);

        productRepository.save(product);
        return productMapper.mapToDto(product);
    }

    @Override
    public ProductDataBaseDto updateProduct(Integer productId, ProductDataBaseDto product) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setUnitPrice(product.getUnitPrice());
        existingProduct.setUnitSize(product.getUnitSize());
        Category category = categoryRepository.findById(product.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + product.getCategoryId()));
        existingProduct.setCategory(category);

        productRepository.save(existingProduct);
        return productMapper.mapToDto(existingProduct);
    }

    @Override
    public ProductDataBaseDto deleteProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));
        productRepository.deleteById(productId);
        return productMapper.mapToDto(product);
    }

}
