package com.example.warehouse.domain.dto.clientAndSupplierDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierWithHistoryDto extends BusinessEntityWithHistoryDto {
    private Integer supplierId;

    @Override
    public void setId(Integer id) {
        this.supplierId = id;
    }
}
