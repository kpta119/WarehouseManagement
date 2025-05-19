package com.example.warehouse.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private String street;
    private Integer streetNumber;
    private String postalCode;
    private String city;
    private Integer countryId;
    private Integer regionId;
}
