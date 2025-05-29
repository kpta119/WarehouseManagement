package com.example.warehouse.mappers;


import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.dto.productDtos.ProductDataBaseDto;
import com.example.warehouse.domain.dto.productDtos.ProductGetSingleProductDto;
import com.example.warehouse.domain.dto.productDtos.ProductSearchEndpointDto;
import com.example.warehouse.domain.dto.transactionDtos.ProductTransactionInfoDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProductMapper {

    public ProductSearchEndpointDto mapToDto(Object[] product, List<Integer> lowStockProductIds, List<Integer> bestSellingProducts) {

        ProductSearchEndpointDto dto = new ProductSearchEndpointDto();

        dto.setProductId(((Number) product[0]).intValue());
        dto.setName((String) product[1]);
        dto.setDescription((String) product[2]);
        dto.setUnitPrice((Double) product[3]);
        dto.setUnitSize((Double) product[4]);
        dto.setInventoryCount(((Number) product[5]).intValue());
        dto.setTransactionCount(((Number) product[6]).intValue());
        dto.setCategoryName((String) product[7]);
        dto.setLowStock(lowStockProductIds.contains(dto.getProductId()));
        dto.setBestSelling(bestSellingProducts.contains(dto.getProductId()));
        return dto;
    }

    public ProductGetSingleProductDto mapToDto(Product product, Map<Integer, Integer> inventory, List<ProductTransactionInfoDto> transactions) {
        ProductGetSingleProductDto dto = new ProductGetSingleProductDto();
        dto.setProductId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setUnitPrice(product.getUnitPrice());
        dto.setUnitSize(product.getUnitSize());
        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getName());
        }
        dto.setInventory(inventory);
        dto.setTransactions(transactions);
        return dto;
    }

    public ProductDataBaseDto mapToDto(Product product) {
        ProductDataBaseDto dto = new ProductDataBaseDto();

        dto.setProductId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setUnitPrice(product.getUnitPrice());
        dto.setUnitSize(product.getUnitSize());
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
        }
        return dto;
    }
}