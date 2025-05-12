package com.example.warehouse.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private Integer id;
    private String type;
    private Date date;
    private String content;
    private Integer warehouseId;
}
