package com.example.warehouse.dtos.InventoryOperationsDtos;

import com.example.warehouse.validation.OnCreate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferBetweenDto {

    @NotNull(message = "fromWarehouseId cannot be null", groups = {OnCreate.class})
    @Positive(message = "fromWarehouseId must be a positive integer", groups = {OnCreate.class})
    private Integer fromWarehouseId;

    @NotNull(message = "toWarehouseId cannot be null", groups = {OnCreate.class})
    @Positive(message = "toWarehouseId must be a positive integer", groups = {OnCreate.class})
    private Integer toWarehouseId;

    @NotNull(message = "Employee Id cannot be null", groups = {OnCreate.class})
    @Positive(message = "Employee Id must be a positive integer", groups = {OnCreate.class})
    private Integer employeeId;

    @NotNull(message = "items cannot be null", groups = {OnCreate.class})
    private Map<String, Integer> items;
}
