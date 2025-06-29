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
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "AddressID")
    private Address address;

    @OneToMany(mappedBy = "warehouse", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private List<ProductInventory> productInventories = new ArrayList<>();

    @OneToMany(mappedBy = "warehouse", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private List<Employee> employees = new ArrayList<>();

}
