package com.example.warehouse.dtos.transactionDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDataBaseDto {
    private Integer transactionId;
    private String TransactionType;
    private String date;
    private String description;
    private Integer employeeId;
    private Integer fromWarehouseId;
    private Integer toWarehouseId;
    private Integer clientId;
    private Integer supplierId;
    private Double sourceWarehouseCapacityAfterTransaction;
    private Double targetWarehouseCapacityAfterTransaction;
}
