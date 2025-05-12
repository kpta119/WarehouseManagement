package com.example.warehouse.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    enum TransactionType {
        WAREHOUSE_TO_WAREHOUSE,
        SUPPLIER_TO_WAREHOUSE,
        WAREHOUSE_TO_CUSTOMER
    }

    private int id;
    private TransactionType transactionType;
    private Date date;
    private String description;
    private Integer employeeId;
    private Integer fromWarehouseId;
    private Integer toWarehouseId;
    private Integer clientId;
    private Integer supplierId;
}
