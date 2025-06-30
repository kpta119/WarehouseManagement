package com.example.warehouse.dtos.filtersDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientSearchFilters {
    private String regionId;
    private Integer minTransactions;
    private Integer maxTransactions;
    private Integer warehouseId;
    private String name;
}
