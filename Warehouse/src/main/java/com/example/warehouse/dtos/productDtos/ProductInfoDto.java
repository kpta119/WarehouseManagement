package com.example.warehouse.dtos.productDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoDto {
    private Integer productId;
    private String name;
    private Integer quantity;
    private Double unitPrice;
    private String categoryName;
}
