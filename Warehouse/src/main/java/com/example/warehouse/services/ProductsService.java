package com.example.warehouse.services;

import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.dto.dateDtos.Period;
import com.example.warehouse.domain.dto.filtersDto.ProductSearchFilterDto;
import com.example.warehouse.domain.dto.productDtos.ProductDataBaseDto;
import com.example.warehouse.domain.dto.productDtos.ProductDetailsResponseDto;
import com.example.warehouse.domain.dto.productDtos.ProductsResponseDto;
import com.example.warehouse.domain.dto.productDtos.ProductsInventoryDto;
import com.example.warehouse.domain.dto.transactionDtos.ProductTransactionInfoDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductsService {

    Page<ProductsResponseDto> getAllProducts(ProductSearchFilterDto productFilters, Pageable pageable);

    ProductDetailsResponseDto getProductDetails(Integer productId);

    List<ProductsInventoryDto> getProductsInventory(Product product);

    List<ProductTransactionInfoDto> getTransactionsDto(Integer productId);

    List<Integer> getLowStockProductIds(Integer warehouseId);

    List<Integer> getBestSellingProducts(Integer warehouseId, Period parsedPeriod);

    ProductDataBaseDto createProduct(@Valid ProductDataBaseDto product);

    ProductDataBaseDto updateProduct(Integer productId, ProductDataBaseDto product);

    ProductDataBaseDto deleteProduct(Integer productId);
}
