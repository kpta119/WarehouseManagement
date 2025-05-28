package com.example.warehouse.services.impl;

import com.example.warehouse.domain.Address;
import com.example.warehouse.domain.City;
import com.example.warehouse.domain.Country;
import com.example.warehouse.domain.Supplier;
import com.example.warehouse.domain.dto.addressDtos.AddressDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.SupplierSummaryDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.SupplierWithHistoryDto;
import com.example.warehouse.mappers.BusinesEntityWithHistoryMapper;
import com.example.warehouse.mappers.BusinessEntitySummaryMapper;
import com.example.warehouse.repositories.AddressRepository;
import com.example.warehouse.repositories.CityRepository;
import com.example.warehouse.repositories.CountryRepository;
import com.example.warehouse.repositories.SupplierRepository;
import com.example.warehouse.services.SupplierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final AddressRepository addressRepository;
    private final SupplierRepository supplierRepository;
    private final BusinessEntitySummaryMapper businessEntitySummaryMapper;
    private final BusinesEntityWithHistoryMapper businessEntityWithHistoryMapper;

    public SupplierServiceImpl(CityRepository cityRepository, CountryRepository countryRepository, AddressRepository addressRepository, SupplierRepository supplierRepository, BusinessEntitySummaryMapper businessEntitySummaryMapper, BusinesEntityWithHistoryMapper businessEntityWithHistoryMapper) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.addressRepository = addressRepository;
        this.supplierRepository = supplierRepository;
        this.businessEntitySummaryMapper = businessEntitySummaryMapper;
        this.businessEntityWithHistoryMapper = businessEntityWithHistoryMapper;
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
        Address addressSaved = addressRepository.save(address);

        Supplier supplier = new Supplier();
        supplier.setName(request.getName());
        supplier.setEmail(request.getEmail());
        supplier.setPhoneNumber(request.getPhoneNumber());
        supplier.setAddress(addressSaved);

        return supplierRepository.save(supplier);
    }

    @Override
    public Page<SupplierSummaryDto> getSuppliersWithTransactionCount(String regionName, Integer minTransactions, Integer maxTransactions, Integer warehouseId,Pageable pageable) {
        Page<Object[]> results = supplierRepository.findAllSuppliersWithTransactionCounts(regionName, minTransactions, maxTransactions, warehouseId, pageable);
        return results.map(businessEntitySummaryMapper::mapToSupplierDto);
    }

    @Override
    public SupplierWithHistoryDto getSupplierWithHistory(Integer supplierId) {
        Supplier supplier = supplierRepository.findSupplierWithHistoryById(supplierId)
                .orElseThrow(() -> new NoSuchElementException("Supplier not found"));
        return businessEntityWithHistoryMapper.mapToDto(supplier);

    }
}
