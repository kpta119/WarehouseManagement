package com.example.warehouse.services.impl;

import com.example.warehouse.domain.Address;
import com.example.warehouse.domain.Employee;
import com.example.warehouse.domain.Transaction;
import com.example.warehouse.domain.Warehouse;
import com.example.warehouse.domain.dto.addressDtos.AddressInfoDto;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseDetailsDto;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseGetAllEndpointDto;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseModifyDto;
import com.example.warehouse.mappers.WarehousesMapper;
import com.example.warehouse.repositories.TransactionRepository;
import com.example.warehouse.repositories.WarehouseRepository;
import com.example.warehouse.services.AddressService;
import com.example.warehouse.services.WarehousesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WarehousesServiceImpl implements WarehousesService {

    private final WarehouseRepository warehouseRepository;
    private final WarehousesMapper warehousesMapper;
    private final TransactionRepository transactionRepository;
    private final AddressService addressService;

    public WarehousesServiceImpl(WarehouseRepository warehouseRepository, WarehousesMapper warehousesMapper,
                                 TransactionRepository transactionRepository, AddressService addressService) {
        this.warehouseRepository = warehouseRepository;
        this.warehousesMapper = warehousesMapper;
        this.transactionRepository = transactionRepository;
        this.addressService = addressService;
    }

    @Override
    public List<WarehouseGetAllEndpointDto> getAllWarehouses() {
        List<Object[]> warehouses = warehouseRepository.findAllWithDetails();
        return warehouses.stream().map(warehousesMapper::mapToDto).toList();
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
}
