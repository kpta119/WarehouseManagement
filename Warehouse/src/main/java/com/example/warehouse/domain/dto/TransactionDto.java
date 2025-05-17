package com.example.warehouse.domain.dto;

import com.example.warehouse.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    enum TransactionType {
        WAREHOUSE_TO_WAREHOUSE,
        SUPPLIER_TO_WAREHOUSE,
        WAREHOUSE_TO_CUSTOMER
    }

    private int id;

    private TransactionDto.TransactionType transactionType;
    private Date date;
    private String description;
    private Employee employee;
    private Warehouse fromWarehouse;
    private Warehouse toWarehouse;
    private Client client;
    private Supplier supplier;
}
