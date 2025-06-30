package com.example.warehouse.mappers;

import com.example.warehouse.domain.Address;
import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.Supplier;
import com.example.warehouse.dtos.clientAndSupplierDtos.ClientDto;
import com.example.warehouse.dtos.clientAndSupplierDtos.SupplierDto;
import org.springframework.stereotype.Component;

@Component
public class BusinessEntityMapper {

    private final AddressMapper addressMapper;

    public BusinessEntityMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public ClientDto mapToDto(Client client) {
        ClientDto dto = new ClientDto();

        dto.setClientId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setPhoneNumber(client.getPhoneNumber());

        Address address = client.getAddress();
        dto.setAddress(addressMapper.mapToDto(address));
        return dto;
    }

    public SupplierDto mapToDto(Supplier supplier) {
        SupplierDto dto = new SupplierDto();

        dto.setSupplierId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setEmail(supplier.getEmail());
        dto.setPhoneNumber(supplier.getPhoneNumber());

        Address address = supplier.getAddress();
        dto.setAddress(addressMapper.mapToDto(address));
        return dto;
    }
}
