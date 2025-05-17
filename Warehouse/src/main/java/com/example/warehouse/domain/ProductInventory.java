package com.example.warehouse.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ProductInventory")
public class ProductInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductInventoryID")
    private Integer id;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Price")
    private Double price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ProductID")
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "WarehouseID")
    private Warehouse warehouse;

}
