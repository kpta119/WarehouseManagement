package com.example.warehouse.dtos.warehouseDto;

import com.example.warehouse.dtos.dateDtos.OccupancyDto;
import com.example.warehouse.dtos.employeeDtos.EmployeeDto;
import com.example.warehouse.dtos.transactionDtos.WarehouseTransactionInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDetailsDto extends WarehouseBase {
    private Integer totalItems;
    private Double totalValue;
    private List<EmployeeDto> employees;
    private List<ProductInWarehouseDto> products;
    private List<WarehouseTransactionInfoDto> transactions;
    private List<OccupancyDto> occupancyHistory;

}
