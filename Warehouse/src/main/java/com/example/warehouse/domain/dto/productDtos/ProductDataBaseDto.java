package com.example.warehouse.domain.dto.productDtos;

import com.example.warehouse.validation.OnCreate;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDataBaseDto extends ProductDto {

    @NotNull(message = "Category ID cannot be null", groups = OnCreate.class)
    private Integer categoryId;
}
