package com.example.warehouse.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
