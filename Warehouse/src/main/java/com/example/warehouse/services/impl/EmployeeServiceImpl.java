package com.example.warehouse.services.impl;

import com.example.warehouse.domain.*;
import com.example.warehouse.domain.dto.addressDtos.AddressDto;
import com.example.warehouse.domain.dto.employeeDtos.EmployeeDto;
import com.example.warehouse.domain.dto.employeeDtos.EmployeeSummaryDto;
import com.example.warehouse.domain.dto.employeeDtos.EmployeeWithHistoryDto;
import com.example.warehouse.mappers.EmployeeSummaryMapper;
import com.example.warehouse.mappers.EmployeeWithHistoryMapper;
import com.example.warehouse.repositories.*;
import com.example.warehouse.services.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final AddressRepository addressRepository;
    private final EmployeeRepository employeeRepository;
    private final WarehouseRepository warehouseRepository;
    private final EmployeeSummaryMapper employeeSummaryMapper;
    private final EmployeeWithHistoryMapper employeeWithHistoryMapper;

    public EmployeeServiceImpl(CityRepository cityRepository, CountryRepository countryRepository, AddressRepository addressRepository, EmployeeRepository employeeRepository, WarehouseRepository warehouseRepository, EmployeeSummaryMapper employeeSummaryMapper, EmployeeWithHistoryMapper employeeWithHistoryMapper) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.addressRepository = addressRepository;
        this.employeeRepository = employeeRepository;
        this.warehouseRepository = warehouseRepository;
        this.employeeSummaryMapper = employeeSummaryMapper;
        this.employeeWithHistoryMapper = employeeWithHistoryMapper;
    }

    @Override
    public Page<EmployeeSummaryDto> getEmployeesWithTransactionCount(String partOfNameOrSurname, String regionName, Integer minTransactions, Integer maxTransactions, Integer warehouseId, Pageable pageable) {
        Page<Object[]> results = employeeRepository.findAllEmployeesWithTransactionCounts(partOfNameOrSurname, regionName, minTransactions, maxTransactions, warehouseId, pageable);
        return results.map(employeeSummaryMapper::mapToDto);
    }

    @Override
    public Employee createEmployee(EmployeeDto request) {
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

        Integer warehouseId = request.getWarehouseId();
        Optional<Warehouse> existingWarehouse = warehouseRepository.findById(warehouseId);
        if (existingWarehouse.isEmpty()){
            throw new NoSuchElementException("Warehouse with this id does not exist");
        }
        Employee employee = new Employee();
        employee.setEmail(request.getEmail());
        employee.setName(request.getName());
        employee.setSurname(request.getSurname());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setPosition(request.getPosition());
        employee.setAddress(addressSaved);
        employee.setWarehouse(existingWarehouse.get());
        return employeeRepository.save(employee);
    }

    @Override
    public EmployeeWithHistoryDto getEmployeeWithHistory(Integer employeeId) {
        Employee employee = employeeRepository.findEmployeeWithHistoryById(employeeId)
                .orElseThrow(()-> new NoSuchElementException("Employee not found"));
        return employeeWithHistoryMapper.mapToDto(employee);
    }
}
