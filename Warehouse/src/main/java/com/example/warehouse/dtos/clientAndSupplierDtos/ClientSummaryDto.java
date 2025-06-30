package com.example.warehouse.dtos.clientAndSupplierDtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientSummaryDto {
    private Integer clientId;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @Email(message = "Wrong email format")
    private String email;
    private String phoneNumber;
    private String address;
    private Long transactionsCount;
}


