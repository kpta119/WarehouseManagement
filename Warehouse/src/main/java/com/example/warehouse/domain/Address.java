package com.example.warehouse.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String street;
    private Integer streetNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CityID")
    private City city;

    @OneToOne(mappedBy = "address")
    private Client client;
}
