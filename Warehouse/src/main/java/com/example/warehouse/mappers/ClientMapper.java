package com.example.warehouse.mappers;

import com.example.warehouse.domain.*;
import com.example.warehouse.domain.dto.AddressDto;
import com.example.warehouse.domain.dto.ClientDto;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientDto mapToDto(Client client) {
        ClientDto dto = new ClientDto();

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
}
