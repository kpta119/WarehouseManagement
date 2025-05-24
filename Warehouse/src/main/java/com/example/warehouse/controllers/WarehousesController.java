package com.example.warehouse.controllers;

import com.example.warehouse.domain.Warehouse;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseModifyDto;
import com.example.warehouse.mappers.WarehousesMapper;
import com.example.warehouse.services.WarehousesService;
import com.example.warehouse.validation.OnCreate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/warehouses")
public class WarehousesController {

    private final WarehousesService warehousesService;
    private final WarehousesMapper warehousesMapper;

    public WarehousesController(WarehousesService warehousesService, WarehousesMapper warehousesMapper) {
        this.warehousesService = warehousesService;
        this.warehousesMapper = warehousesMapper;
    }

    @GetMapping()
    public ResponseEntity<?> getAllWarehouses() {
        try {
            return ResponseEntity.ok(warehousesService.getAllWarehouses());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @GetMapping("/{warehouseId}")
    public ResponseEntity<?> getWarehouseById(@PathVariable Integer warehouseId) {
        try {
            return ResponseEntity.ok(warehousesService.getWarehouseById(warehouseId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Warehouse not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createWarehouse(@Validated(OnCreate.class) @RequestBody WarehouseModifyDto warehouseDto) {
        try {
            Warehouse warehouse = warehousesService.createWarehouse(warehouseDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(warehousesMapper.mapToDto(warehouse));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }
}
