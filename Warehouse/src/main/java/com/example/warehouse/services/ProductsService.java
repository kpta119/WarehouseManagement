package com.example.warehouse.services;

import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.dto.dateDtos.Period;
import com.example.warehouse.domain.dto.productDtos.ProductDataBaseDto;
import com.example.warehouse.domain.dto.transactionDtos.TransactionDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface ProductsService {

    List<Product> getAllProducts(String name, Integer categoryId, Double minPrice, Double maxPrice, Double minSize, Double maxSize, Integer warehouseId);

    Integer getInventoryCount(Integer productId, Integer warehouseId);

    Integer getTransactionCount(Integer productId, Integer warehouseId);

    Product getProductById(Integer productId);

    Map<Integer, Integer> getInventoryMap(Integer productId);

    List<TransactionDto> getTransactionsDto(Integer productId);

    List<Integer> getLowStockProductIds(Integer warehouseId, int lowStockThreshold);

    List<Integer> getBestSellingProducts(Integer warehouseId, Period parsedPeriod, int topN);

    Product createProduct(@Valid ProductDataBaseDto product);

    Product updateProduct(Integer productId, ProductDataBaseDto product);

    Product deleteProduct(Integer productId);
}
