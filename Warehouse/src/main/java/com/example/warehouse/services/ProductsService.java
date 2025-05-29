package com.example.warehouse.services;

import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.dto.dateDtos.Period;
import com.example.warehouse.domain.dto.filtersDto.ProductSearchFilterDto;
import com.example.warehouse.domain.dto.productDtos.ProductDataBaseDto;
import com.example.warehouse.domain.dto.transactionDtos.ProductTransactionInfoDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ProductsService {

    Page<Object[]> getAllProducts(ProductSearchFilterDto productFilters, Pageable pageable);

    Product getProductByIdWithProductInventory(Integer productId);

    Map<Integer, Integer> getInventoryMap(Product product);

    List<ProductTransactionInfoDto> getTransactionsDto(Integer productId);

    List<Integer> getLowStockProductIds(Integer warehouseId, int lowStockThreshold);

    List<Integer> getBestSellingProducts(Integer warehouseId, Period parsedPeriod, int topN);

    Product createProduct(@Valid ProductDataBaseDto product);

    Product updateProduct(Integer productId, ProductDataBaseDto product);

    Product deleteProduct(Integer productId);
}
