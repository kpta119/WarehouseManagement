package com.example.warehouse.dtos.productDtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ProductsResponseDto extends ProductDto {
    private String categoryName;
    private Long inventoryCount;
    private Long transactionCount;
    private Boolean isLowStock;
    private Boolean isBestSelling;

    public ProductsResponseDto(
            Integer productId,
            String name,
            String description,
            Double unitPrice,
            Double unitSize,
            String categoryName,
            long inventoryCount,
            long transactionCount
    ) {
        super(productId, name, description, unitPrice, unitSize);
        this.categoryName = categoryName;
        this.inventoryCount = inventoryCount;
        this.transactionCount = transactionCount;
    }
}
