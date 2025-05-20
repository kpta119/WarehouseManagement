package com.example.warehouse.mappers.products;


import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.dto.productDtos.ProductSearchEndpointDto;
import org.springframework.stereotype.Component;

@Component
public class ProductSearchEndpointMapper {

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
}