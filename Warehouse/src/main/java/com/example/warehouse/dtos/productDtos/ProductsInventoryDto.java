package com.example.warehouse.dtos.productDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsInventoryDto {
    private Integer warehouseId;
    private String warehouseName;
    private Integer quantity;
}
