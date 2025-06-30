package com.example.warehouse.dtos.addressDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {
    private Integer id;
    private String name;
    private String countryCode;
    private Integer regionId;
}
