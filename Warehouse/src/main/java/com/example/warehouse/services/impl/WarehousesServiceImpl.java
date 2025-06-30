package com.example.warehouse.services.impl;

import com.example.warehouse.domain.*;
import com.example.warehouse.domain.dto.filtersDto.WarehousesSearchFilters;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseDataBaseDto;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseDetailsDto;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseGetAllResponseDto;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseModifyDto;
import com.example.warehouse.mappers.WarehousesMapper;
import com.example.warehouse.repositories.TransactionRepository;
import com.example.warehouse.repositories.WarehouseRepository;
import com.example.warehouse.services.AddressService;
import com.example.warehouse.services.WarehousesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WarehousesServiceImpl implements WarehousesService {

    private final WarehouseRepository warehouseRepository;
    private final WarehousesMapper warehousesMapper;
    private final TransactionRepository transactionRepository;
    private final AddressService addressService;

    public WarehousesServiceImpl(
            WarehouseRepository warehouseRepository,
            WarehousesMapper warehousesMapper,
            TransactionRepository transactionRepository,
            AddressService addressService
    ) {
        this.warehouseRepository = warehouseRepository;
        this.warehousesMapper = warehousesMapper;
        this.transactionRepository = transactionRepository;
        this.addressService = addressService;
    }

    @Override
    public Page<WarehouseGetAllResponseDto> getAllWarehouses(WarehousesSearchFilters filters, Pageable pageable) {
        return warehouseRepository.findAllWithDetails(filters, pageable);
    }

    @Override
    public WarehouseDetailsDto getWarehouseById(Integer warehouseId) {
        Warehouse warehouse = warehouseRepository.findWithDetailsById(warehouseId)
                .orElseThrow(() -> new NoSuchElementException("Warehouse not found with ID: " + warehouseId));
        List<Transaction> transactions = transactionRepository.findAllByWarehouseId(warehouseId);
        List<Employee> employees = warehouse.getEmployees();
        Integer totalItems = countTotalItemsInWarehouse(warehouse);
        Double totalValue = calculateTotalValueInWarehouse(warehouse);
        return warehousesMapper.mapToDto(warehouse, transactions, employees, totalItems, totalValue);
    }

    @Override
    public Integer countTotalItemsInWarehouse(Warehouse warehouse) {
        return warehouse.getProductInventories().stream()
                .mapToInt(ProductInventory::getQuantity)
                .sum();
    }

    @Override
    public Double calculateTotalValueInWarehouse(Warehouse warehouse) {
        return warehouse.getProductInventories().stream()
                .mapToDouble(productInventory -> productInventory.getQuantity() * productInventory.getProduct().getUnitPrice())
                .sum();
    }

    @Override
    public WarehouseDataBaseDto createWarehouse(WarehouseModifyDto warehouse) {
        Warehouse newWarehouse = new Warehouse();
        Address address = addressService.createAddress(warehouse);
        newWarehouse.setAddress(address);
        newWarehouse.setName(warehouse.getName());
        newWarehouse.setCapacity(warehouse.getCapacity());
        newWarehouse.setOccupiedCapacity(0.0);

        Warehouse createdWarehouse = warehouseRepository.save(newWarehouse);
        return warehousesMapper.mapToDto(createdWarehouse);
    }

    @Override
    public WarehouseDataBaseDto updateWarehouse(WarehouseModifyDto warehouseDto, Integer warehouseId) {
        Warehouse existingWarehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new NoSuchElementException("Warehouse not found with ID: " + warehouseId));
        existingWarehouse.setName(warehouseDto.getName());
        existingWarehouse.setCapacity(warehouseDto.getCapacity());
        Address address = existingWarehouse.getAddress();
        Address updatedAddress = addressService.updateAddress(warehouseDto, address);
        existingWarehouse.setAddress(updatedAddress);
        Warehouse updatedWarehouse = warehouseRepository.save(existingWarehouse);
        return warehousesMapper.mapToDto(updatedWarehouse);
    }

    @Override
    public WarehouseDataBaseDto deleteWarehouse(Integer warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new NoSuchElementException("Warehouse not found with ID: " + warehouseId));
        warehouseRepository.delete(warehouse);
        return warehousesMapper.mapToDto(warehouse);
    }
}
