package com.example.warehouse.domain.dto.addressDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    @NotBlank(message = "Street cannot be empty")
    private String street;

    @NotNull(message = "Street number cannot be null")
    @Positive(message = "Street number must be a positive number")
    private Integer streetNumber;
    private String postalCode;
    private String city;
    private Integer countryId;
    private Integer regionId;
}
