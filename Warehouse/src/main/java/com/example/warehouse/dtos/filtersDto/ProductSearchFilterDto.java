package com.example.warehouse.dtos.filtersDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchFilterDto {
    private String  name;
    private Integer categoryId;
    private Double minPrice;
    private Double  maxPrice;
    private Double  minSize;
    private Double  maxSize;
    private Integer warehouseId;
    private Integer minInventory;
    private Integer maxInventory;
    private Integer minTransactions;
    private Integer maxTransactions;
}
