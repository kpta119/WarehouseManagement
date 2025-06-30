package com.example.warehouse.domain.dto.warehouseDto;

import com.example.warehouse.domain.dto.addressDtos.AddressInfoDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseModifyDto extends AddressInfoDto {
    private Integer warehouseId;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Capacity cannot be null")
    @Positive(message = "Capacity must be positive")
    private Double capacity;
}
