package com.example.warehouse.dtos.addressDtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionDto {
    private Integer id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
}
