package com.example.warehouse.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReportID")
    private Integer id;

    private String type;
    private Date date;
    private String content;

    @ManyToOne(cascade = CascadeType.ALL,
               optional = false)
    @JoinColumn(name = "WarehouseID")
    private Warehouse warehouse;
}
