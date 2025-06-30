package com.example.warehouse.dtos.transactionDtos;

import com.example.warehouse.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSummaryDto {
    private Integer transactionId;
    private Date date;
    private String description;
    private Transaction.TransactionType type;
    private String employeeName;
    private String fromWarehouseName;
    private String toWarehouseName;
    private String clientName;
    private String supplierName;
    private Double totalPrice;
    private Double totalSize;
}
