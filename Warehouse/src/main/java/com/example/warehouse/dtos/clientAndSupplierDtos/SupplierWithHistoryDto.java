package com.example.warehouse.dtos.clientAndSupplierDtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"supplierId", "name", "email", "phoneNumber", "address", "history"})
public class SupplierWithHistoryDto extends BusinessEntityWithHistoryDto {
    private Integer supplierId;

    @Override
    public void setId(Integer id) {
        this.supplierId = id;
    }
}
