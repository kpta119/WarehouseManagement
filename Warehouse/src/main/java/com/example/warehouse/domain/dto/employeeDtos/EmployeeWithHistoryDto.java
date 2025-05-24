package com.example.warehouse.domain.dto.employeeDtos;

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
public class EmployeeWithHistoryDto {
    private Integer employeeId;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Surname cannot be empty")
    private String surname;
    @Email(message = "Wrong email format")
    private String email;
    private String phoneNumber;
    private String position;
    private Integer warehouseId;
    private String warehouseName;
    private Integer transactionsCount;
    private AddressDto address;
    private List<TransactionWithProductsDto> history;
}
