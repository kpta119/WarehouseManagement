package com.example.warehouse.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Transaction")
public class Transaction {
    enum TransactionType {
        WAREHOUSE_TO_WAREHOUSE,
        SUPPLIER_TO_WAREHOUSE,
        WAREHOUSE_TO_CUSTOMER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionID")
    private Integer id;

    private TransactionType transactionType;
    private Date date;
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "EmployeeID")
    private Employee employee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FromWarehouseID")
    private Warehouse fromWarehouse;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ToWarehouseID")
    private Warehouse toWarehouse;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ClientID")
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SupplierID")
    private Supplier supplier;
}
