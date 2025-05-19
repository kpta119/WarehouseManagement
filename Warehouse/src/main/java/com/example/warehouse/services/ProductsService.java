package com.example.warehouse.services;

import com.example.warehouse.domain.Product;

import java.util.List;

public interface ProductsService {

    List<Product> getAllProducts(String name, Integer categoryId, Double minPrice, Double maxPrice, Double minSize, Double maxSize, Integer warehouseId);

}
