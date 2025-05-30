package com.example.warehouse.domain.dto.warehouseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseGetAllEndpointDto extends WarehouseBase {
    private Integer employeesCount;
    private Integer productsSum;
    private Integer transactionsCount;
}
