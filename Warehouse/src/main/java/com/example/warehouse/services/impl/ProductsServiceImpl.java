package com.example.warehouse.services.impl;

import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.ProductInventory;
import com.example.warehouse.domain.dto.TransactionDtos.TransactionDto;
import com.example.warehouse.repositories.ProductInventoryRepository;
import com.example.warehouse.repositories.ProductRepository;
import com.example.warehouse.repositories.TransactionProductRepository;
import com.example.warehouse.services.ProductsService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {
    private final ProductRepository productRepository;
    private final ProductInventoryRepository productInventoryRepository;
    private final TransactionProductRepository transactionProductRepository;

    public ProductsServiceImpl(ProductRepository productRepository, ProductInventoryRepository productInventoryRepository, TransactionProductRepository transactionProductRepository) {
        this.productRepository = productRepository;
        this.productInventoryRepository = productInventoryRepository;
        this.transactionProductRepository = transactionProductRepository;
    }

    @Override
    public List<Product> getAllProducts(String name, Integer categoryId, Double minPrice, Double maxPrice, Double minSize, Double maxSize, Integer warehouseId) {
        return productRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (categoryId != null) {
                predicates.add(cb.equal(root.get("category").get("id"), categoryId));
            }
            if (minPrice != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("unitPrice"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("unitPrice"), maxPrice));
            }
            if (minSize != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("unitSize"), minSize));
            }
            if (maxSize != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("unitSize"), maxSize));
            }

            if (warehouseId != null) {
                Join<Product, ProductInventory> inventoryJoin = root.join("productInventories", JoinType.INNER);
                predicates.add(cb.equal(inventoryJoin.get("warehouse").get("id"), warehouseId));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    @Override
    public Integer getInventoryCount(Integer productId, Integer warehouseId) {
        Integer inventoryCount;
        if (warehouseId != null) {
            inventoryCount = productInventoryRepository.sumInventoryQuantityByProductIdAndWarehouseId(productId, warehouseId);
        } else {
            inventoryCount = productInventoryRepository.sumInventoryQuantityByProductId(productId);
        }
        return inventoryCount != null ? inventoryCount : 0;
    }

    @Override
    public Integer getTransactionCount(Integer productId, Integer warehouseId) {
        Integer transactionCount;
        if (warehouseId != null) {
            transactionCount = transactionProductRepository.countTransactionsByProductIdAndWarehouseId(productId, warehouseId);
        } else {
            transactionCount = transactionProductRepository.countTransactionsByProductId(productId);
        }
        return transactionCount != null ? transactionCount : 0;
    }

    @Override
    public Product getProductById(Integer productId) {
        return productRepository.findById(productId).orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));
    }

    @Override
    public Map<Integer, Integer> getInventoryMap(Integer productId) {
        List<ProductInventory> productInventories = productInventoryRepository.findByProductId(productId);
        return productInventories.stream()
                .collect(Collectors.toMap(
                        pi -> pi.getWarehouse().getId(),
                        ProductInventory::getQuantity,
                        Integer::sum
                ));
    }

    @Override
    public List<TransactionDto> getTransactionsDto(Integer productId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return transactionProductRepository.findByProductId(productId).stream()
                .map(transactionProduct -> new TransactionDto(
                        transactionProduct.getTransaction().getId(),
                        simpleDateFormat.format(transactionProduct.getTransaction().getDate()),
                        transactionProduct.getTransaction().getTransactionType().name(),
                        transactionProduct.getTransactionPrice(),
                        transactionProduct.getQuantity()
                ))
                .collect(Collectors.toList());
    }

}
