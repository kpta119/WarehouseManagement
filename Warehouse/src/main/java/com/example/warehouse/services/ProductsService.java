package com.example.warehouse.services;

import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.dto.dateDtos.Period;
import com.example.warehouse.domain.dto.productDtos.ProductDataBaseDto;
import com.example.warehouse.domain.dto.transactionDtos.ProductTransactionInfoDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface ProductsService {

    List<Object[]> getAllProducts(String name, Integer categoryId, Double minPrice, Double maxPrice, Double minSize, Double maxSize, Integer warehouseId);

    Product getProductByIdWithProductInventory(Integer productId);

    Map<Integer, Integer> getInventoryMap(Product product);

    List<ProductTransactionInfoDto> getTransactionsDto(Integer productId);

    List<Integer> getLowStockProductIds(Integer warehouseId, int lowStockThreshold);

    List<Integer> getBestSellingProducts(Integer warehouseId, Period parsedPeriod, int topN);

    Product createProduct(@Valid ProductDataBaseDto product);

    Product updateProduct(Integer productId, ProductDataBaseDto product);

    Product deleteProduct(Integer productId);
}
