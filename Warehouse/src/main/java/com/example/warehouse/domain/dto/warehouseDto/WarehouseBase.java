package com.example.warehouse.domain.dto.warehouseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseBase {
    private Integer warehouseId;
    private String name;
    private Double capacity;
    private Double occupiedCapacity;
    private String address;
}
