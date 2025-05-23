package com.example.warehouse.services;

import com.example.warehouse.domain.dto.warehouseDto.WarehouseDetailsDto;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseGetAllEndpointDto;

import java.util.List;

public interface WarehousesService {
    List<WarehouseGetAllEndpointDto> getAllWarehouses();

    WarehouseDetailsDto getWarehouseById(Integer warehouseId);
}
