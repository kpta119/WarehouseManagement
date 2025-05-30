package com.example.warehouse.services.impl;

import com.example.warehouse.domain.*;
import com.example.warehouse.domain.dto.addressDtos.AddressInfoDto;
import com.example.warehouse.domain.dto.filtersDto.WarehousesSearchFilters;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseDetailsDto;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseGetAllEndpointDto;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseModifyDto;
import com.example.warehouse.mappers.WarehousesMapper;
import com.example.warehouse.repositories.CityRepository;
import com.example.warehouse.repositories.CountryRepository;
import com.example.warehouse.repositories.TransactionRepository;
import com.example.warehouse.repositories.WarehouseRepository;
import com.example.warehouse.services.AddressService;
import com.example.warehouse.services.WarehousesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class WarehousesServiceImpl implements WarehousesService {

    private final WarehouseRepository warehouseRepository;
    private final WarehousesMapper warehousesMapper;
    private final TransactionRepository transactionRepository;
    private final AddressService addressService;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    public WarehousesServiceImpl(WarehouseRepository warehouseRepository, WarehousesMapper warehousesMapper,
                                 TransactionRepository transactionRepository, AddressService addressService,
                                 CityRepository cityRepository, CountryRepository countryRepository) {
        this.warehouseRepository = warehouseRepository;
        this.warehousesMapper = warehousesMapper;
        this.transactionRepository = transactionRepository;
        this.addressService = addressService;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public Page<WarehouseGetAllEndpointDto> getAllWarehouses(WarehousesSearchFilters filters, Pageable pageable) {
        Page<Object[]> warehouses = warehouseRepository.findAllWithDetails(filters, pageable);
        return warehouses.map(warehousesMapper::mapToDto);
    }

    @Override
    public WarehouseDetailsDto getWarehouseById(Integer warehouseId) {
        Warehouse warehouse = warehouseRepository.findWithDetailsById(warehouseId)
                .orElseThrow(() -> new NoSuchElementException("Warehouse not found with ID: " + warehouseId));
        List<Transaction> transactions = transactionRepository.findAllByWarehouseId(warehouseId);
        List<Employee> employees = warehouse.getEmployees();
        return warehousesMapper.mapToDto(warehouse, transactions, employees);
    }

    @Override
    public Warehouse createWarehouse(WarehouseModifyDto warehouse) {
        Warehouse newWarehouse = new Warehouse();
        AddressInfoDto addressInfoDto = new AddressInfoDto();
        addressInfoDto.setStreet(warehouse.getStreet());
        addressInfoDto.setStreetNumber(Integer.valueOf(warehouse.getStreetNumber()));
        addressInfoDto.setCityName(warehouse.getCity());
        addressInfoDto.setPostalCode(warehouse.getPostalCode());
        addressInfoDto.setCountryId(warehouse.getCountryId());
        Address address = addressService.createAddress(addressInfoDto);
        newWarehouse.setAddress(address);
        newWarehouse.setName(warehouse.getName());
        newWarehouse.setCapacity(warehouse.getCapacity());
        newWarehouse.setOccupiedCapacity(0.0);

        return warehouseRepository.save(newWarehouse);
    }

    @Override
    public Warehouse updateWarehouse(WarehouseModifyDto warehouseDto, Integer warehouseId) {
        Warehouse existingWarehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new NoSuchElementException("Warehouse not found with ID: " + warehouseId));
        Address Address = existingWarehouse.getAddress();
        if (warehouseDto.getName() != null) {
            existingWarehouse.setName(warehouseDto.getName());
        }
        if (warehouseDto.getCapacity() != null) {
            existingWarehouse.setCapacity(warehouseDto.getCapacity());
        }
        if (warehouseDto.getStreetNumber() != null) {
            Address.setStreetNumber(Integer.valueOf(warehouseDto.getStreetNumber()));
        }
        if (warehouseDto.getStreet() != null) {
            Address.setStreet(warehouseDto.getStreet());
        }
        if (warehouseDto.getCity() != null && warehouseDto.getPostalCode() != null && warehouseDto.getCountryId() != null) {
            Optional<City> foundCity = cityRepository.findByPostalCodeAndNameAndCountry_Id(
                    warehouseDto.getPostalCode(), warehouseDto.getCity(), warehouseDto.getCountryId());
            Country foundCountry = countryRepository.findById(warehouseDto.getCountryId())
                    .orElseThrow(() -> new NoSuchElementException("Country not found with ID: " + warehouseDto.getCountryId()));
            City city = foundCity.orElseGet(() -> {
                City newCity = new City();
                newCity.setName(warehouseDto.getCity());
                newCity.setPostalCode(warehouseDto.getPostalCode());
                newCity.setCountry(foundCountry);
                return cityRepository.save(newCity);
            });
            Address.setCity(city);
        }
        return warehouseRepository.save(existingWarehouse);
    }

    @Override
    public Warehouse deleteWarehouse(Integer warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new NoSuchElementException("Warehouse not found with ID: " + warehouseId));
        warehouseRepository.delete(warehouse);
        return warehouse;
    }
}
