package com.example.warehouse.domain.dto.TransactionDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSummaryDto {
    private Integer transactionId;
    private String date;
    private String description;
    private String type;
    private Integer employeeId;
    private Integer fromWarehouseId;
    private Integer toWarehouseId;
    private Integer clientId;
    private Integer supplierId;
    private Double totalPrice;
}
