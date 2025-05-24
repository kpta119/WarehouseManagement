package com.example.warehouse.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClientID")
    private Integer id;

    private String name;
    private String email;
    private String phoneNumber;

    @OneToOne(
            optional = false,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "AddressID")
    private Address address;

    @OneToMany(mappedBy = "client")
    private List<Transaction> transactions;
}
