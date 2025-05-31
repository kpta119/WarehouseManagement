package com.example.warehouse.domain.dto.filtersDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSearchFilter {
    private String partOfNameOrSurname;
    private String regionId;
    private Integer minTransaction;
    private Integer maxTransactions;
    private Integer warehouseId;
}