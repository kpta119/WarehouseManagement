package com.example.warehouse.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInventory {
    private Integer id;
    private Integer productId;
    private Integer warehouseId;
    private Integer quantity;
}
