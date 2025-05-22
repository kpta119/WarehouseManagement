package com.example.warehouse.domain.dto.addressDtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressInfoDto {
    @NotBlank(message = "City name cannot be empty")
    private String cityName;
    @NotBlank(message = "Postal code cannot be empty")
    private String postalCode;
    @NotBlank(message = "Street cannot be empty")
    private String street;
    private Integer streetNumber;
    private Integer countryId;
}
