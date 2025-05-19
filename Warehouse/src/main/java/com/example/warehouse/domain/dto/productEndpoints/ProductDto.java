package com.example.warehouse.domain.dto.productEndpoints;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Integer productId;
    private String name;
    private String description;
    private Double unitPrice;
    private Double unitSize;
}