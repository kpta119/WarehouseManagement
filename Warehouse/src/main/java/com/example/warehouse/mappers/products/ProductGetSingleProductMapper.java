package com.example.warehouse.mappers.products;

import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.dto.transactionDtos.TransactionDto;
import com.example.warehouse.domain.dto.productDtos.ProductGetSingleProductDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProductGetSingleProductMapper {

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
}
