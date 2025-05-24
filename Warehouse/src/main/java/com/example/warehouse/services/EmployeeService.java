package com.example.warehouse.services;

import com.example.warehouse.domain.Employee;
import com.example.warehouse.domain.dto.employeeDtos.EmployeeDto;
import com.example.warehouse.domain.dto.employeeDtos.EmployeeSummaryDto;
import com.example.warehouse.domain.dto.employeeDtos.EmployeeWithHistoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    List<EmployeeSummaryDto> getEmployeesWithTransactionCount(Integer warehouseId);

    Employee createEmployee(EmployeeDto request);

    EmployeeWithHistoryDto getEmployeeWithHistory(Integer employeeId);
}
