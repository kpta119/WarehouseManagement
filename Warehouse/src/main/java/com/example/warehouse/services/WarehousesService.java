package com.example.warehouse.services;

import com.example.warehouse.domain.Warehouse;
import com.example.warehouse.domain.dto.filtersDto.WarehousesSearchFilters;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseDetailsDto;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseGetAllEndpointDto;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseModifyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WarehousesService {
    Page<WarehouseGetAllEndpointDto> getAllWarehouses(WarehousesSearchFilters filters, Pageable pageable);

    WarehouseDetailsDto getWarehouseById(Integer warehouseId);

    Integer countTotalItemsInWarehouse(Warehouse warehouse);

    Double calculateTotalValueInWarehouse(Warehouse warehouse);

    Warehouse createWarehouse(WarehouseModifyDto warehouseDto);

    Warehouse updateWarehouse(WarehouseModifyDto warehouseDto, Integer warehouseId);

    Warehouse deleteWarehouse(Integer warehouseId);
}
