package com.example.warehouse.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    @NotBlank(message = "Street cannot be empty")
    private String street;

    private Integer streetNumber;
    private String postalCode;
    private String city;
    private Integer countryId;
    private Integer regionId;
}
