package com.example.warehouse.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Client")
public class Client implements BusinessEntity{

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

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private List<Transaction> transactions;

    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getEmail() {
        return email;
    }
    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }
    @Override
    public Address getAddress() {
        return address;
    }
}
