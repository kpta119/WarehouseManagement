package com.example.warehouse.mappers;

import com.example.warehouse.domain.Employee;
import com.example.warehouse.domain.dto.employeeDtos.EmployeeWithHistoryDto;
import com.example.warehouse.domain.dto.transactionDtos.TransactionWithProductsDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeWithHistoryMapper {
    private final TransactionMapper transactionMapper;
    private final AddressMapper addressMapper;

    public EmployeeWithHistoryMapper(TransactionMapper transactionMapper, AddressMapper addressMapper) {
        this.transactionMapper = transactionMapper;
        this.addressMapper = addressMapper;
    }

    public EmployeeWithHistoryDto mapToDto(Employee employee){
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
        List<TransactionWithProductsDto> listOfTransactions = employee.getTransactions()
                .stream()
                .map(transactionMapper::mapToDto)
                .toList();
        dto.setHistory(listOfTransactions);
        dto.setTransactionsCount(listOfTransactions.size());
        return dto;
    }
}
