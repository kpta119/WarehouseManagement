package com.example.warehouse.services;

import com.example.warehouse.domain.Warehouse;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseDetailsDto;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseGetAllEndpointDto;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseModifyDto;

import java.util.List;

public interface WarehousesService {
    List<WarehouseGetAllEndpointDto> getAllWarehouses();

    WarehouseDetailsDto getWarehouseById(Integer warehouseId);

    Warehouse createWarehouse(WarehouseModifyDto warehouseDto);

    Warehouse updateWarehouse(WarehouseModifyDto warehouseDto, Integer warehouseId);
}
