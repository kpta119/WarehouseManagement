package com.example.warehouse.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private Integer transactionId;
    private String type;
    private Date date;
    private String description;
    private Integer employeeId;
    private Integer fromWarehouseId;
    private Integer toWarehouseId;
    private Integer clientId;
    private Integer supplierId;
    private List<ProductInfoDto> products;
}
