package com.example.warehouse.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Transaction")
public class Transaction {

    public enum TransactionType {
        WAREHOUSE_TO_WAREHOUSE,
        SUPPLIER_TO_WAREHOUSE,
        WAREHOUSE_TO_CUSTOMER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionID")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private Date date;
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "EmployeeID")
    private Employee employee;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "FromWarehouseID")
    private Warehouse fromWarehouse;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ToWarehouseID")
    private Warehouse toWarehouse;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ClientID")
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "SupplierID")
    private Supplier supplier;

    @Column(name = "SourceWarehouseCapacityAfterTransaction")
    private Double sourceWarehouseCapacityAfterTransaction;

    @Column(name = "TargetWarehouseCapacityAfterTransaction")
    private Double targetWarehouseCapacityAfterTransaction;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionProduct> products;

    public Double getTotalPrice() {
        return products.stream()
                .mapToDouble(transactionProduct ->
                        transactionProduct.getTransactionPrice() * transactionProduct.getQuantity())
                .sum();
    }
}
