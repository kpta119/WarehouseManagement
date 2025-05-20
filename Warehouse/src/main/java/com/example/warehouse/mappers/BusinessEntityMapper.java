package com.example.warehouse.mappers;

import com.example.warehouse.domain.*;
import com.example.warehouse.domain.dto.AddressDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import org.springframework.stereotype.Component;

@Component
public class BusinessEntityMapper {

    public BusinessEntityDto mapToDto(Client client) {
        BusinessEntityDto dto = new BusinessEntityDto();

        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setPhoneNumber(client.getPhoneNumber());

        Address address = client.getAddress();
        if (address != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(address.getStreet());
            addressDto.setStreetNumber(address.getStreetNumber());

            City city = address.getCity();
            if (city != null) {
                addressDto.setCity(city.getName());
                addressDto.setPostalCode(city.getPostalCode());

                if (city.getCountry() != null) {
                    addressDto.setCountryId(city.getCountry().getId());
                }

                if (city.getCountry().getRegion() != null) {
                    addressDto.setRegionId(city.getCountry().getRegion().getId());
                }
            }
            dto.setAddress(addressDto);
        }
        return dto;
    }

    public BusinessEntityDto mapToDto(Supplier supplier) {
        BusinessEntityDto dto = new BusinessEntityDto();

        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setEmail(supplier.getEmail());
        dto.setPhoneNumber(supplier.getPhoneNumber());

        Address address = supplier.getAddress();
        if (address != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(address.getStreet());
            addressDto.setStreetNumber(address.getStreetNumber());

            City city = address.getCity();
            if (city != null) {
                addressDto.setCity(city.getName());
                addressDto.setPostalCode(city.getPostalCode());

                if (city.getCountry() != null) {
                    addressDto.setCountryId(city.getCountry().getId());
                }

                if (city.getCountry().getRegion() != null) {
                    addressDto.setRegionId(city.getCountry().getRegion().getId());
                }
            }

            dto.setAddress(addressDto);
        }
        return dto;
    }
}
