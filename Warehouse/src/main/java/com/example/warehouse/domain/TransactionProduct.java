package com.example.warehouse.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TransactionProduct")
public class TransactionProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionProductID")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TransactionID")
    private Transaction transaction;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ProductID")
    private Product product;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "TransactionPrice")
    private Double transactionPrice;
}
