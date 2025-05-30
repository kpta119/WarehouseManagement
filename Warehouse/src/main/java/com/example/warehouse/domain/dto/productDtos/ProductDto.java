package com.example.warehouse.domain.dto.productDtos;

import com.example.warehouse.validation.OnCreate;
import com.example.warehouse.validation.OnUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Integer productId;

    @NotBlank(message = "Name cannot be empty", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotBlank(message = "Description cannot be empty", groups = {OnCreate.class, OnUpdate.class})
    private String description;

    @NotNull(message = "Unit price cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Positive(message = "Unit price must be positive", groups = {OnCreate.class, OnUpdate.class})
    private Double unitPrice;

    @NotNull(message = "Unit size cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Positive(message = "Unit size must be positive", groups = {OnCreate.class, OnUpdate.class})
    private Double unitSize;
}