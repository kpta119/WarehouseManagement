package com.example.warehouse.domain.dto.filtersDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehousesSearchFilters {
    private String name;
    private Double minCapacity;
    private Double maxCapacity;
    private Double minOccupied;
    private Double maxOccupied;
    private Integer regionId;
    private Integer minEmployees;
    private Integer maxEmployees;
    private Integer minProducts;
    private Integer maxProducts;
    private Integer minTransactions;
    private Integer maxTransactions;
}