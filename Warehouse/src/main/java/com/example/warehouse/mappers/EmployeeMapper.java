package com.example.warehouse.mappers;

import com.example.warehouse.domain.Employee;
import com.example.warehouse.dtos.addressDtos.AddressDto;
import com.example.warehouse.dtos.employeeDtos.EmployeeDto;
import com.example.warehouse.dtos.employeeDtos.EmployeeWithHistoryDto;
import com.example.warehouse.dtos.transactionDtos.TransactionWithProductsDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeMapper {
    private final AddressMapper addressMapper;

    public EmployeeMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public EmployeeDto mapToDto(Employee employee){
        EmployeeDto dto = new EmployeeDto();
        dto.setEmployeeId(employee.getId());
        dto.setEmail(employee.getEmail());
        dto.setName(employee.getName());
        dto.setSurname(employee.getSurname());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setPosition(employee.getPosition());
        dto.setWarehouseId(employee.getWarehouse().getId());
        AddressDto address = addressMapper.mapToDto(employee.getAddress());
        dto.setAddress(address);
        return dto;
    }

    public EmployeeWithHistoryDto mapToDto(Employee employee, List<TransactionWithProductsDto> transactions) {
        EmployeeWithHistoryDto dto = new EmployeeWithHistoryDto();
        dto.setEmployeeId(employee.getId());
        dto.setName(employee.getName());
        dto.setSurname(employee.getSurname());
        dto.setEmail(employee.getEmail());
        dto.setPosition(employee.getPosition());
        dto.setWarehouseId(employee.getWarehouse().getId());
        dto.setWarehouseName(employee.getWarehouse().getName());
        dto.setAddress(addressMapper.mapToDto(employee.getAddress()));
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setHistory(transactions);
        dto.setTransactionsCount(transactions.size());
        return dto;
    }
}
