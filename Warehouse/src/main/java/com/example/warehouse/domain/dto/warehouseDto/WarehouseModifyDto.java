package com.example.warehouse.domain.dto.warehouseDto;

import com.example.warehouse.validation.OnCreate;
import com.example.warehouse.validation.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseModifyDto {
    private Integer warehouseId;

    @NotBlank(message = "Name cannot be blank", groups = {OnCreate.class})
    private String name;

    @NotNull(message = "Capacity cannot be null", groups = {OnCreate.class})
    @Positive(message = "Capacity must be positive", groups = {OnCreate.class, OnUpdate.class})
    private Double capacity;

    @NotNull(message = "RegionId cannot be null", groups = {OnCreate.class})
    @Positive(message = "RegionId must be positive", groups = {OnCreate.class, OnUpdate.class})
    private Integer regionId;

    @NotNull(message = "CountryId cannot be null", groups = {OnCreate.class})
    @Positive(message = "CountryId must be positive", groups = {OnCreate.class, OnUpdate.class})
    private Integer countryId;

    @NotBlank(message = "City cannot be blank", groups = {OnCreate.class})
    private String city;

    @NotBlank(message = "Postal code cannot be blank", groups = {OnCreate.class})
    private String postalCode;

    @NotBlank(message = "Street cannot be blank", groups = {OnCreate.class})
    private String street;

    @NotBlank(message = "Street number cannot be blank", groups = {OnCreate.class})
    private String streetNumber;
}
