package com.example.warehouse.mappers;

import com.example.warehouse.domain.Address;
import com.example.warehouse.domain.City;
import com.example.warehouse.domain.dto.addressDtos.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressDto mapToDto(Address address) {
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
        return addressDto;
    }
}
