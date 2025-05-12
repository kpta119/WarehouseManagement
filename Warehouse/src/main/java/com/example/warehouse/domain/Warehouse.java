package com.example.warehouse.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {
    private Integer id;
    private String name;
    private Integer capacity;
    private Integer occupiedCapacity;
    private Integer addressId;
}
