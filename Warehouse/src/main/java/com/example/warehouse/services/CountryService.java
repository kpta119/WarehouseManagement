package com.example.warehouse.services;

import com.example.warehouse.domain.dto.addressDtos.CountryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CountryService {
    List<CountryDto> getAllCountries(Integer regionId);
}
