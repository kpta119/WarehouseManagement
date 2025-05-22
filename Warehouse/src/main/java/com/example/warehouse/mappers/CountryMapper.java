package com.example.warehouse.mappers;

import com.example.warehouse.domain.Country;
import com.example.warehouse.domain.dto.addressDtos.CountryDto;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {
    public CountryDto mapToDto(Country country){
        CountryDto dto =  new CountryDto();
        dto.setId(country.getId());
        dto.setName(country.getName());
        dto.setCountryCode(country.getCountryCode());
        dto.setRegionId(country.getRegion().getId());
        return dto;
    }
}
