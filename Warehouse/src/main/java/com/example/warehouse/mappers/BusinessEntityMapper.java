package com.example.warehouse.mappers;

import com.example.warehouse.domain.Address;
import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.Supplier;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import org.springframework.stereotype.Component;

@Component
public class BusinessEntityMapper {

    private final AddressMapper addressMapper;

    public BusinessEntityMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public BusinessEntityDto mapToDto(Client client) {
        BusinessEntityDto dto = new BusinessEntityDto();

        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setPhoneNumber(client.getPhoneNumber());

        Address address = client.getAddress();
        dto.setAddress(addressMapper.mapToDto(address));
        return dto;
    }

    public BusinessEntityDto mapToDto(Supplier supplier) {
        BusinessEntityDto dto = new BusinessEntityDto();

        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setEmail(supplier.getEmail());
        dto.setPhoneNumber(supplier.getPhoneNumber());

        Address address = supplier.getAddress();
        dto.setAddress(addressMapper.mapToDto(address));
        return dto;
    }
}
