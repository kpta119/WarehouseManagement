package com.example.warehouse.dtos.warehouseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDataBaseDto {
    private Integer warehouseId;
    private String name;
    private Double capacity;
    private Double occupiedCapacity;
    private Integer addressId;
}
