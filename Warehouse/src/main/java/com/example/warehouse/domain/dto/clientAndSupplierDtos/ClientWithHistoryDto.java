package com.example.warehouse.domain.dto.clientAndSupplierDtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"clientId", "name", "email", "phoneNumber", "address", "history"})
public class ClientWithHistoryDto extends BusinessEntityWithHistoryDto {
    private Integer clientId;

    @Override
    public void setId(Integer id) {
        this.clientId = id;
    }
}
