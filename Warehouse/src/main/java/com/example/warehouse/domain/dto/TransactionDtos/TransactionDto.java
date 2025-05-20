package com.example.warehouse.domain.dto.TransactionDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private Integer transactionId;
    private String date;
    private String type;
    private Double price;
    private Integer quantity;
}
