package com.example.warehouse.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    private Integer id;
    private String name;
    private String email;
    private String phoneNumber;
    private Integer addressId;
}
