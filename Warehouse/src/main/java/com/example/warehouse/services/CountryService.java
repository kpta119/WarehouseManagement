package com.example.warehouse.services;

import com.example.warehouse.domain.Country;
import com.example.warehouse.dtos.addressDtos.CountryDto;
import com.example.warehouse.mappers.CountryMapper;
import com.example.warehouse.repositories.CountryRepository;
import com.example.warehouse.repositories.RegionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CountryService {
    private final CountryMapper countryMapper;
    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;

    public CountryService(CountryMapper countryMapper, CountryRepository countryRepository, RegionRepository regionRepository) {
        this.countryMapper = countryMapper;
        this.countryRepository = countryRepository;
        this.regionRepository = regionRepository;
    }

    public List<CountryDto> getAllCountries(Integer regionId){
        if (regionId != null && !regionRepository.existsById(regionId)) {
            throw new NoSuchElementException("Region does not exist");
        }
        List<Country> countries = countryRepository.findAllByRegionId(regionId);
        return countries.stream()
                .map(countryMapper::mapToDto)
                .toList();
    }
}
