package com.example.warehouse.dtos.clientAndSupplierDtos;

import com.example.warehouse.dtos.transactionDtos.TransactionWithProductsDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BusinessEntityWithHistoryDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @Email(message = "Wrong email format")
    private String email;
    private String phoneNumber;
    private String address;
    private List<TransactionWithProductsDto> history;

    public abstract void setId(Integer id);
}
