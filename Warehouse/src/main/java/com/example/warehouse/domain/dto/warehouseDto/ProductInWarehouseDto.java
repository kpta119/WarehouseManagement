package com.example.warehouse.domain.dto.warehouseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInWarehouseDto {
    private Integer productId;
    private String name;
    private Integer quantity;
    private Double unitPrice;
}
