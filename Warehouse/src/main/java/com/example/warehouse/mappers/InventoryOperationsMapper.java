package com.example.warehouse.mappers;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.dtos.transactionDtos.TransactionDataBaseDto;
import org.springframework.stereotype.Component;

@Component
public class InventoryOperationsMapper {

    public TransactionDataBaseDto mapToDto(Transaction transaction) {
        TransactionDataBaseDto dto = new TransactionDataBaseDto();

        dto.setTransactionId(transaction.getId());
        dto.setTransactionType(transaction.getTransactionType().name());
        dto.setDate(transaction.getDate().toString());
        dto.setDescription(transaction.getDescription());
        dto.setEmployeeId(transaction.getEmployee().getId());

        if(transaction.getFromWarehouse() != null) {
            dto.setFromWarehouseId(transaction.getFromWarehouse().getId());
        } else {
            dto.setFromWarehouseId(null);
        }
        if(transaction.getToWarehouse() != null) {
            dto.setToWarehouseId(transaction.getToWarehouse().getId());
        } else {
            dto.setToWarehouseId(null);
        }
        if(transaction.getClient() != null) {
            dto.setClientId(transaction.getClient().getId());
        } else {
            dto.setClientId(null);
        }
        if(transaction.getSupplier() != null) {
            dto.setSupplierId(transaction.getSupplier().getId());
        } else {
            dto.setSupplierId(null);
        }
        dto.setSourceWarehouseCapacityAfterTransaction(transaction.getSourceWarehouseCapacityAfterTransaction());
        dto.setTargetWarehouseCapacityAfterTransaction(transaction.getTargetWarehouseCapacityAfterTransaction());

        return dto;
    }
}
