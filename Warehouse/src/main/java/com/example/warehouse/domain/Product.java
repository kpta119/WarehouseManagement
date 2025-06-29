package com.example.warehouse.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    @ManyToOne()
    @JoinColumn(name = "CategoryID")
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private List<ProductInventory> productInventories = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private List<TransactionProduct> productTransactions = new ArrayList<>();
}
