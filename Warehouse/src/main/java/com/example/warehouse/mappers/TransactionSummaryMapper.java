package com.example.warehouse.mappers;

import com.example.warehouse.domain.dto.transactionDtos.TransactionSummaryDto;
import org.springframework.stereotype.Component;

@Component
public class TransactionSummaryMapper {

    public TransactionSummaryDto mapToDto(Object[] transaction) {
        TransactionSummaryDto dto = new TransactionSummaryDto();

        dto.setTransactionId((Integer) transaction[0]);
        dto.setType(String.valueOf(transaction[3]));
        dto.setDate(String.valueOf(transaction[1]));
        dto.setDescription((String) transaction[2]);
        dto.setEmployeeName(transaction[4] + " " + transaction[5]);
        dto.setFromWarehouseName(transaction[6] != null ? (String) transaction[6] : null);
        dto.setToWarehouseName(transaction[7] != null ? (String) transaction[7] : null);
        dto.setClientName(transaction[8] != null ? (String) transaction[8] : null);
        dto.setSupplierName(transaction[9] != null ? (String) transaction[9] : null);
        dto.setTotalPrice(((Number) transaction[10]).doubleValue());
        dto.setTotalSize(((Number) transaction[11]).doubleValue());

        return dto;

    }
}
