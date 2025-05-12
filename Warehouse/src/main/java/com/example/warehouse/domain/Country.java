package com.example.warehouse.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    private Integer id;
    private String name;
    private String countryCode;
    private Integer regionId;
}
