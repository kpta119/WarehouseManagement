package com.example.warehouse.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionProduct {
    private Integer id;
    private Integer transactionId;
    private Integer productId;
    private Integer quantity;
}
