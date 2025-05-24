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
    @Column(name = "AddressID")
    private Integer id;

    private String street;
    private Integer streetNumber;

    @ManyToOne
    @JoinColumn(name = "CityID")
    private City city;

    @OneToOne(mappedBy = "address")
    private Client client;

    @OneToOne(mappedBy = "address")
    private Supplier supplier;


    @Override
    public String toString() {
        return getCity().getCountry().getName() + " " + city.getName() + " " + street + " " + streetNumber;
    }
}
