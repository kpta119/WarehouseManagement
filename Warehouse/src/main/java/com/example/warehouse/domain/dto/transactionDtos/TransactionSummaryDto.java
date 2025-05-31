package com.example.warehouse.domain.dto.transactionDtos;

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
    private String employeeName;
    private String fromWarehouseName;
    private String toWarehouseName;
    private String clientName;
    private String supplierName;
    private Double totalPrice;
    private Double totalSize;
}
