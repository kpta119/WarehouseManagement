package com.example.warehouse.mappers;


import com.example.warehouse.domain.Product;
import com.example.warehouse.dtos.productDtos.ProductDataBaseDto;
import com.example.warehouse.dtos.productDtos.ProductDetailsResponseDto;
import com.example.warehouse.dtos.productDtos.ProductsInventoryDto;
import com.example.warehouse.dtos.transactionDtos.ProductTransactionInfoDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public ProductDetailsResponseDto mapToDto(Product product, List<ProductsInventoryDto> inventory, List<ProductTransactionInfoDto> transactions) {
        ProductDetailsResponseDto dto = new ProductDetailsResponseDto();
        dto.setProductId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setUnitPrice(product.getUnitPrice());
        dto.setUnitSize(product.getUnitSize());
        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getName());
            dto.setCategoryId(product.getCategory().getId());
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