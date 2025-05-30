package com.example.warehouse.domain.dto.clientAndSupplierDtos;

import com.example.warehouse.domain.dto.addressDtos.AddressDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDto {
    private Integer supplierId;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    private String phoneNumber;

    @Valid
    private AddressDto address;
}