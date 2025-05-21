package com.example.warehouse.services.impl;

import com.example.warehouse.domain.*;
import com.example.warehouse.domain.dto.addressDtos.AddressDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.repositories.*;
import com.example.warehouse.services.SupplierService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final AddressRepository addressRepository;
    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(CityRepository cityRepository, CountryRepository countryRepository, AddressRepository addressRepository, SupplierRepository supplierRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.addressRepository = addressRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Supplier createSupplier(BusinessEntityDto request) {
        AddressDto addressDto = request.getAddress();
        Optional<City> existingCity = cityRepository.findByPostalCodeAndNameAndCountry_Id(
                addressDto.getPostalCode(), addressDto.getCity(), addressDto.getCountryId()
        );
        City city;
        if (existingCity.isEmpty()){
            Country country = countryRepository.findById(addressDto.getCountryId())
                    .orElseThrow(() -> new NoSuchElementException("Country not found"));

            city = new City();
            city.setCountry(country);
            city.setName(addressDto.getCity());
            city.setPostalCode(addressDto.getPostalCode());
            cityRepository.save(city);
        } else {
            city = existingCity.get();
        }

        Address address = new Address();
        address.setCity(city);
        address.setStreet(addressDto.getStreet());
        address.setStreetNumber(addressDto.getStreetNumber());
        address = addressRepository.save(address);

        Supplier supplier = new Supplier();
        supplier.setName(request.getName());
        supplier.setEmail(request.getEmail());
        supplier.setPhoneNumber(request.getPhoneNumber());
        supplier.setAddress(address);

        return supplierRepository.save(supplier);
    }
}
