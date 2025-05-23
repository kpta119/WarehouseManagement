package com.example.warehouse.services.impl;

import com.example.warehouse.domain.dto.warehouseDto.WarehouseGetAllEndpointDto;
import com.example.warehouse.mappers.WarehousesMapper;
import com.example.warehouse.repositories.WarehouseRepository;
import com.example.warehouse.services.WarehousesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehousesServiceImpl implements WarehousesService {

    private final WarehouseRepository warehouseRepository;
    private final WarehousesMapper warehousesMapper;

    public WarehousesServiceImpl(WarehouseRepository warehouseRepository, WarehousesMapper warehousesMapper) {
        this.warehouseRepository = warehouseRepository;
        this.warehousesMapper = warehousesMapper;
    }

    @Override
    public List<WarehouseGetAllEndpointDto> getAllWarehouses() {
        List<Object[]> warehouses = warehouseRepository.findAllWithDetails();
        return warehouses.stream().map(warehousesMapper::mapToDto).toList();
    }
}
