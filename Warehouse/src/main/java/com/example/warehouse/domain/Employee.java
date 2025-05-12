package com.example.warehouse.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Integer id;
    private String name;
    private String surname;
    private String position;
    private String email;
    private String phoneNumber;
    private Integer addressId;
    private Integer warehouseId;
}
