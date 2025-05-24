package com.example.warehouse.mappers;

import com.example.warehouse.domain.Employee;
import com.example.warehouse.domain.dto.employeeDtos.EmployeeSummaryDto;
import org.springframework.stereotype.Component;

@Component
public class EmployeeSummaryMapper {
    public EmployeeSummaryDto mapToDto(Object[] queryResult){
        Employee employee = (Employee) queryResult[0];
        Long transactionFromDb = (Long) queryResult[1];
        Integer transactionCount = transactionFromDb.intValue();

        EmployeeSummaryDto dto = new EmployeeSummaryDto();
        dto.setEmail(employee.getEmail());
        dto.setEmployeeId(employee.getId());
        dto.setName(employee.getName());
        dto.setSurname(employee.getSurname());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setPosition(employee.getPosition());
        dto.setTransactionsCount(transactionCount);
        dto.setWarehouseName(employee.getWarehouse().getName());
        return dto;
    }
}
