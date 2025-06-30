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
public class SellToClientDto {

    @NotNull(message = "Warehouse Id cannot be null", groups = {OnCreate.class})
    @Positive(message = "Warehouse Id must be a positive integer", groups = {OnCreate.class})
    private Integer warehouseId;

    @NotNull(message = "clientId cannot be null", groups = {OnCreate.class})
    @Positive(message = "clientId must be a positive integer", groups = {OnCreate.class})
    private Integer clientId;

    @NotNull(message = "Employee Id cannot be null", groups = {OnCreate.class})
    @Positive(message = "Employee Id must be a positive integer", groups = {OnCreate.class})
    private Integer employeeId;

    @NotNull(message = "items cannot be null", groups = {OnCreate.class})
    private Map<String, Integer> items;
}
