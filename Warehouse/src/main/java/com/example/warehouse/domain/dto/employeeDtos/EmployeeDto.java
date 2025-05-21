package com.example.warehouse.domain.dto.employeeDtos;

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
public class EmployeeDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Surname cannot be empty")
    private String surname;
    @Email(message = "Wrong email format")
    private String email;
    private String phoneNumber;
    private String position;
    private Integer warehouseId;
    @Valid
    private AddressDto address;
}
