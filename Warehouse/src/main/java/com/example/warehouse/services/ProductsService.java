package com.example.warehouse.services;

import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.dto.dateDtos.Period;
import com.example.warehouse.domain.dto.filtersDto.ProductSearchFilterDto;
import com.example.warehouse.domain.dto.productDtos.ProductDataBaseDto;
import com.example.warehouse.domain.dto.productDtos.ProductsInventoryDto;
import com.example.warehouse.domain.dto.transactionDtos.ProductTransactionInfoDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductsService {

    Page<Object[]> getAllProducts(ProductSearchFilterDto productFilters, Pageable pageable);

    Product getProductByIdWithProductInventory(Integer productId);

    List<ProductsInventoryDto> getProductsInventory(Product product);

    List<ProductTransactionInfoDto> getTransactionsDto(Integer productId);

    List<Integer> getLowStockProductIds(Integer warehouseId, int lowStockThreshold);

    List<Integer> getBestSellingProducts(Integer warehouseId, Period parsedPeriod, int topN);

    Product createProduct(@Valid ProductDataBaseDto product);

    Product updateProduct(Integer productId, ProductDataBaseDto product);

    Product deleteProduct(Integer productId);
}
