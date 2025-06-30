package com.example.warehouse.dtos.transactionDtos;

import com.example.warehouse.dtos.productDtos.ProductInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionWithProductsDto {
    private Integer transactionId;
    private String type;
    private String date;
    private String description;
    private String employeeName;
    private Integer employeeId;
    private Integer fromWarehouseId;
    private String fromWarehouseName;
    private Integer toWarehouseId;
    private String toWarehouseName;
    private Integer clientId;
    private String clientName;
    private Integer supplierId;
    private String supplierName;
    private Integer totalItems;
    private Double totalPrice;
    private List<ProductInfoDto> products;
}
