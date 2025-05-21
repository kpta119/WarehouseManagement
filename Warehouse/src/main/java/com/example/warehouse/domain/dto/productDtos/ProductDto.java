package com.example.warehouse.domain.dto.productDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Integer productId;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Unit price cannot be null")
    @Positive(message = "Unit price must be positive")
    private Double unitPrice;

    @NotNull(message = "Unit size cannot be null")
    @Positive(message = "Unit size must be positive")
    private Double unitSize;
}