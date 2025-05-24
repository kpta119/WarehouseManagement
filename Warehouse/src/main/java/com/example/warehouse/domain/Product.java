package com.example.warehouse.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private Integer id;

    private String name;
    private String description;
    private Double unitPrice;
    private Double unitSize;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CategoryID")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductInventory> productInventories = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<TransactionProduct> productTransactions = new ArrayList<>();
}
