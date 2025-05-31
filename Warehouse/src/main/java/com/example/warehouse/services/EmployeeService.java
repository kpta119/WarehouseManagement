package com.example.warehouse.services;

import com.example.warehouse.domain.Employee;
import com.example.warehouse.domain.dto.employeeDtos.EmployeeDto;
import com.example.warehouse.domain.dto.employeeDtos.EmployeeSummaryDto;
import com.example.warehouse.domain.dto.employeeDtos.EmployeeWithHistoryDto;
import com.example.warehouse.domain.dto.filtersDto.EmployeeSearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    Page<EmployeeSummaryDto> getEmployeesWithTransactionCount(EmployeeSearchFilter filters, Pageable pageable);

    Employee createEmployee(EmployeeDto request);

    EmployeeWithHistoryDto getEmployeeWithHistory(Integer employeeId);
}
