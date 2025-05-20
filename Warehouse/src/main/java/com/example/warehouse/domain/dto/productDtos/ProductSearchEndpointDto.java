package com.example.warehouse.domain.dto.productDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchEndpointDto extends ProductDto {
    private String categoryName;
    private Integer inventoryCount;
    private Integer transactionCount;
}
