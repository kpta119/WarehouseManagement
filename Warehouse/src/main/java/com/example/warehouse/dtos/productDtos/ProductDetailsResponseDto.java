package com.example.warehouse.dtos.productDtos;

import com.example.warehouse.dtos.transactionDtos.ProductTransactionInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsResponseDto extends ProductDto {
    private String categoryName;
    private Integer categoryId;
    private List<ProductsInventoryDto> inventory;
    private List<ProductTransactionInfoDto> transactions;

}
