package com.example.warehouse.domain.dto.clientAndSupplierDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientWithHistoryDto extends BusinessEntityWithHistoryDto {
    private Integer clientId;

    @Override
    public void setId(Integer id) {
        this.clientId = id;
    }
}
