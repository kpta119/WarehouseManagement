package com.example.warehouse.domain.dto.warehouseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseGetAllResponseDto extends WarehouseBase {
    private Long employeesCount;
    private Long productsSum;
    private Long transactionsCount;

    public WarehouseGetAllResponseDto(
            Integer warehouseId,
            String name,
            Double capacity,
            Double occupiedCapacity,
            String address,
            long employeesCount,
            long productsSum,
            long transactionsCount) {
        super(warehouseId, name, capacity, occupiedCapacity, address);
        this.employeesCount = employeesCount;
        this.productsSum = productsSum;
        this.transactionsCount = transactionsCount;
    }
}
