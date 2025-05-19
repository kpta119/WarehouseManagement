package com.example.warehouse.services.impl;

import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.ProductInventory;
import com.example.warehouse.repositories.ProductRepository;
import com.example.warehouse.services.ProductsService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {
    private final ProductRepository productRepository;

    public ProductsServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
                Join<Product, ProductInventory> inventoryJoin = root.join("productInventories", JoinType.LEFT);
                predicates.add(cb.equal(inventoryJoin.get("warehouseID"), warehouseId));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}
