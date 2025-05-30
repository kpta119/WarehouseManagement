package com.example.warehouse.domain.dto.transactionDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTransactionInfoDto {
    private Integer transactionId;
    private String date;
    private String type;
    private Double price;
    private Integer quantity;
    private Integer employeeId;
    private String employeeName;
}
