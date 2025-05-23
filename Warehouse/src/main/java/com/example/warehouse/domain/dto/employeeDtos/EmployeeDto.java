package com.example.warehouse.domain.dto.employeeDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Integer employeeId;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String position;
}
