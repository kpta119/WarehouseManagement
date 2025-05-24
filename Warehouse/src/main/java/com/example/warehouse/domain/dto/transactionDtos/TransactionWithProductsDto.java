package com.example.warehouse.domain.dto.transactionDtos;

import com.example.warehouse.domain.dto.productDtos.ProductInfoDto;
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
    private Integer employeeId;
    private Integer fromWarehouseId;
    private Integer toWarehouseId;
    private Integer clientId;
    private Integer supplierId;
    private List<ProductInfoDto> products;
}
