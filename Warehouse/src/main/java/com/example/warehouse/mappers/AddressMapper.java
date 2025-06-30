package com.example.warehouse.mappers;

import com.example.warehouse.domain.Address;
import com.example.warehouse.domain.City;
import com.example.warehouse.dtos.addressDtos.AddressDto;
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
                addressDto.setCountryName(city.getCountry().getName());
                if (city.getCountry().getRegion() != null) {
                    addressDto.setRegionId(city.getCountry().getRegion().getId());
                    addressDto.setRegionName(city.getCountry().getRegion().getName());
                }
            }

        }
        return addressDto;
    }
}
