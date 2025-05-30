package com.example.warehouse.services.impl;

import com.example.warehouse.domain.*;
import com.example.warehouse.domain.dto.dateDtos.Period;
import com.example.warehouse.domain.dto.filtersDto.ProductSearchFilterDto;
import com.example.warehouse.domain.dto.productDtos.ProductDataBaseDto;
import com.example.warehouse.domain.dto.productDtos.ProductsInventoryDto;
import com.example.warehouse.domain.dto.transactionDtos.ProductTransactionInfoDto;
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
    private final ProductInventoryRepository productInventoryRepository;
    private final TransactionProductRepository transactionProductRepository;
    private final CategoryRepository categoryRepository;
    private final Clock clock;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ProductsServiceImpl(ProductRepository productRepository, ProductInventoryRepository productInventoryRepository, TransactionProductRepository transactionProductRepository, CategoryRepository categoryRepository, Clock clock) {
        this.productRepository = productRepository;
        this.productInventoryRepository = productInventoryRepository;
        this.transactionProductRepository = transactionProductRepository;
        this.categoryRepository = categoryRepository;
        this.clock = clock;
    }

    @Override
    public Page<Object[]> getAllProducts(ProductSearchFilterDto productFilters, Pageable pageable) {
        return productRepository.findAllProducts(productFilters, pageable);
    }

    @Override
    public Product getProductByIdWithProductInventory(Integer productId) {
        return productRepository.findByIdWithProductInventory(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));
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
        Product product = productRepository.findByIdWithTransactionProduct(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));

        List<TransactionProduct> transactions = product.getProductTransactions();
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
    public List<Integer> getLowStockProductIds(Integer warehouseId, int lowStockThreshold) {
        return (warehouseId != null) ? productInventoryRepository.findLowStockProductsByWarehouseId(warehouseId, lowStockThreshold)
                : productInventoryRepository.findLowStockProducts(lowStockThreshold);
    }


    @Override
    public List<Integer> getBestSellingProducts(Integer warehouseId, Period parsedPeriod, int topN) {
        LocalDate now = LocalDate.now(clock);
        Date fromDate = switch (parsedPeriod) {
            case week -> Date.valueOf(now.minusWeeks(1));
            case month -> Date.valueOf(now.minusMonths(1));
            case year -> Date.valueOf(now.minusYears(1));
            case allTime -> Date.valueOf(LocalDate.of(1970, 1, 1));
        };
        PageRequest pageRequest = PageRequest.of(0, topN);
        return (warehouseId != null) ? transactionProductRepository.findTopNBestSellingProductsByWarehouseId(warehouseId, fromDate, pageRequest)
                : transactionProductRepository.findTopNBestSellingProducts(fromDate, pageRequest);
    }

    @Override
    public Product createProduct(ProductDataBaseDto productDto) {
        Product product = new Product();
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + productDto.getCategoryId()));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setUnitPrice(productDto.getUnitPrice());
        product.setUnitSize(productDto.getUnitSize());
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Integer productId, ProductDataBaseDto product) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setUnitPrice(product.getUnitPrice());
        existingProduct.setUnitSize(product.getUnitSize());
        Category category = categoryRepository.findById(product.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + product.getCategoryId()));
        existingProduct.setCategory(category);

        return productRepository.save(existingProduct);
    }

    @Override
    public Product deleteProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));
        productRepository.deleteById(productId);
        return product;
    }

}
