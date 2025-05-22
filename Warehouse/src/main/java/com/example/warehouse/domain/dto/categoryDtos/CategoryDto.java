package com.example.warehouse.domain.dto.categoryDtos;

import com.example.warehouse.validation.OnCreate;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Integer categoryId;

    @NotBlank(message = "Name cannot be blank", groups = {OnCreate.class})
    private String name;
    @NotBlank(message = "Description cannot be blank", groups = {OnCreate.class})
    private String description;
}
