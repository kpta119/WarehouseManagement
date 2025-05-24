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
@Table(name = "Warehouse")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WarehouseID")
    private Integer id;

    private String name;
    private Double capacity;
    @Column(name = "OccupiedCapacity", nullable = false, columnDefinition = "DOUBLE DEFAULT 0")
    private Double occupiedCapacity;

    @OneToOne(
            optional = false,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "AddressID")
    private Address address;

    @OneToMany(mappedBy = "warehouse")
    private List<ProductInventory> productInventories = new ArrayList<>();

    @OneToMany(mappedBy = "warehouse")
    private List<Employee> employees = new ArrayList<>();

}
