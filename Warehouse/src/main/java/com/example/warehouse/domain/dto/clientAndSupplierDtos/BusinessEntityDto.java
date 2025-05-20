package com.example.warehouse.domain.dto.clientAndSupplierDtos;

import com.example.warehouse.domain.dto.AddressDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessEntityDto {
    private Integer id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    private String phoneNumber;

    @Valid
    private AddressDto address;
}

class SupplierDto extends BusinessEntityDto {}
class ClientDto extends BusinessEntityDto {}
