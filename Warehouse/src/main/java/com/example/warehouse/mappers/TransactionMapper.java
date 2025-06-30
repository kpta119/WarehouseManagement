package com.example.warehouse.mappers;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.dtos.productDtos.ProductInfoDto;
import com.example.warehouse.dtos.transactionDtos.TransactionWithProductsDto;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class TransactionMapper {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public TransactionWithProductsDto mapToDto(Transaction transaction, List<ProductInfoDto> products, Integer totalItems, Double totalPrice) {
        TransactionWithProductsDto dto = new TransactionWithProductsDto();

        dto.setTransactionId(transaction.getId());
        dto.setType(transaction.getTransactionType().name());
        dto.setDate(simpleDateFormat.format(transaction.getDate()));
        dto.setDescription(transaction.getDescription());

        if (transaction.getEmployee() != null) {
            dto.setEmployeeName(transaction.getEmployee().getName() + " " + transaction.getEmployee().getSurname());
            dto.setEmployeeId(transaction.getEmployee().getId());
        }

        if (transaction.getFromWarehouse() != null) {
            dto.setFromWarehouseId(transaction.getFromWarehouse().getId());
            dto.setFromWarehouseName(transaction.getFromWarehouse().getName());
        }

        if (transaction.getToWarehouse() != null) {
            dto.setToWarehouseId(transaction.getToWarehouse().getId());
            dto.setToWarehouseName(transaction.getToWarehouse().getName());
        }

        if (transaction.getClient() != null) {
            dto.setClientId(transaction.getClient().getId());
            dto.setClientName(transaction.getClient().getName());
        }

        if (transaction.getSupplier() != null) {
            dto.setSupplierId(transaction.getSupplier().getId());
            dto.setSupplierName(transaction.getSupplier().getName());
        }
        dto.setTotalItems(totalItems);
        dto.setTotalPrice(totalPrice);
        dto.setProducts(products);

        return dto;
    }
}
