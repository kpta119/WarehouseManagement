package com.example.warehouse.domain.dto.categoryDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Integer categoryId;
    private String name;
    private String description;
}
