package com.example.warehouse.dtos.addressDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressInfoDto {
    @NotBlank(message = "City name cannot be empty")
    private String city;
    @NotBlank(message = "Postal code cannot be blank")
    private String postalCode;
    @NotBlank(message = "Street cannot be empty")
    private String street;
    @Positive(message = "Street number cannot be negative or zero")
    @NotNull(message = "Street number cannot be null")
    private Integer streetNumber;
    @Positive(message = "CountryId cannot be negative or zero")
    @NotNull(message = "CountryId cannot be null")
    private Integer countryId;
}
