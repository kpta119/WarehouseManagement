package com.example.warehouse.mappers;

import com.example.warehouse.domain.Employee;
import com.example.warehouse.domain.dto.addressDtos.AddressDto;
import com.example.warehouse.domain.dto.employeeDtos.EmployeeDto;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    private final AddressMapper addressMapper;

    public EmployeeMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public EmployeeDto mapToDto(Employee employee){
        EmployeeDto dto = new EmployeeDto();
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
}
