package com.example.warehouse.domain.dto.clientAndSupplierDtos;

import com.example.warehouse.domain.dto.addressDtos.AddressDto;
import com.example.warehouse.domain.dto.transactionDtos.TransactionWithProductsDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierWithHistoryDto {
    private Integer supplierId;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @Email(message = "Wrong email format")
    private String email;
    private String phoneNumber;
    private AddressDto address;
    private List<TransactionWithProductsDto> history;
}
