package com.example.warehouse.services;

import com.example.warehouse.domain.Address;
import com.example.warehouse.domain.City;
import com.example.warehouse.domain.Country;
import com.example.warehouse.dtos.addressDtos.AddressInfoDto;
import com.example.warehouse.repositories.AddressRepository;
import com.example.warehouse.repositories.CityRepository;
import com.example.warehouse.repositories.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AddressService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final AddressRepository addressRepository;

    public AddressService(CityRepository cityRepository, CountryRepository countryRepository, AddressRepository addressRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.addressRepository = addressRepository;
    }

    public Address createAddress(AddressInfoDto addressDto) {
        Country country = countryRepository.findById(addressDto.getCountryId())
                .orElseThrow(() -> new NoSuchElementException("Country not found with ID: " + addressDto.getCountryId()));

        String postalCode = addressDto.getPostalCode();
        String cityName = addressDto.getCity();
        Integer countryId = addressDto.getCountryId();
        Optional<City> foundCity = cityRepository.findByPostalCodeAndNameAndCountry_Id(postalCode, cityName, countryId);

        City city = foundCity.orElseGet(() -> {
            City newCity = new City();
            newCity.setName(addressDto.getCity());
            newCity.setPostalCode(addressDto.getPostalCode());
            newCity.setCountry(country);
            return cityRepository.save(newCity);
        });

        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setStreetNumber(addressDto.getStreetNumber());
        address.setCity(city);

        return addressRepository.save(address);
    }

    public Address updateAddress(AddressInfoDto addressDto, Address oldAddress) {
        Address newAddress = createAddress(addressDto);
        addressRepository.delete(oldAddress);
        return newAddress;
    }
}
