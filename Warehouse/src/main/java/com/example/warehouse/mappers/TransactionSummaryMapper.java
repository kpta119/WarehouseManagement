package com.example.warehouse.mappers;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.domain.dto.TransactionSummaryDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

@Component
public class TransactionSummaryMapper {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public TransactionSummaryDto mapToDto(Transaction transaction) {
        TransactionSummaryDto dto = new TransactionSummaryDto();

        dto.setTransactionId(transaction.getId());
        dto.setType(transaction.getTransactionType().name());
        dto.setDate(simpleDateFormat.format(transaction.getDate()));
        dto.setDescription(transaction.getDescription());

        if (transaction.getEmployee() != null) {
            dto.setEmployeeId(transaction.getEmployee().getId());
        }

        if (transaction.getFromWarehouse() != null) {
            dto.setFromWarehouseId(transaction.getFromWarehouse().getId());
        }

        if (transaction.getToWarehouse() != null) {
            dto.setToWarehouseId(transaction.getToWarehouse().getId());
        }

        if (transaction.getClient() != null) {
            dto.setClientId(transaction.getClient().getId());
        }

        if (transaction.getSupplier() != null) {
            dto.setSupplierId(transaction.getSupplier().getId());
        }

        Double totalPrice = transaction.getProducts().stream()
                .map(tp -> BigDecimal.valueOf(tp.getTransactionPrice()).multiply(BigDecimal.valueOf(tp.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .doubleValue();

        dto.setTotalPrice(totalPrice);

        return dto;

    }
}
