package com.example.warehouse.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "City")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String postalCode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CountryID")
    private Country country;
}
