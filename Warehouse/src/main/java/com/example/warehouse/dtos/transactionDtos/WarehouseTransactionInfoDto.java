package com.example.warehouse.dtos.transactionDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseTransactionInfoDto {
    private Integer transactionId;
    private String date;
    private String description;
    private String type;
    private Double totalPrice;
    private Integer employeeId;
    private String employeeName;

}
