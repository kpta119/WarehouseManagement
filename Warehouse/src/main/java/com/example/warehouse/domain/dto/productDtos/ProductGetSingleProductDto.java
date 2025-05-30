package com.example.warehouse.domain.dto.productDtos;

import com.example.warehouse.domain.dto.transactionDtos.ProductTransactionInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductGetSingleProductDto extends ProductDto {
    private String categoryName;
    private Integer categoryId;
    private List<ProductsInventoryDto> inventory;
    private List<ProductTransactionInfoDto> transactions;

}
