package com.example.warehouse.mappers;


import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.dto.productDtos.ProductDataBaseDto;
import com.example.warehouse.domain.dto.productDtos.ProductGetSingleProductDto;
import com.example.warehouse.domain.dto.productDtos.ProductSearchEndpointDto;
import com.example.warehouse.domain.dto.transactionDtos.TransactionDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProductMapper {

    public ProductSearchEndpointDto mapToDto(Product product, int inventoryCount, int transactionCount) {

        ProductSearchEndpointDto dto = new ProductSearchEndpointDto();

        dto.setProductId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setUnitPrice(product.getUnitPrice());
        dto.setUnitSize(product.getUnitSize());
        dto.setInventoryCount(inventoryCount);
        dto.setTransactionCount(transactionCount);
        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getName());
        }
        return dto;
    }

    public ProductGetSingleProductDto mapToDto(Product product, Map<Integer, Integer> inventory, List<TransactionDto> transactions) {
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