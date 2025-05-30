package com.example.warehouse.controllers;

import com.example.warehouse.domain.Warehouse;
import com.example.warehouse.domain.dto.filtersDto.WarehousesSearchFilters;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseModifyDto;
import com.example.warehouse.mappers.WarehousesMapper;
import com.example.warehouse.services.WarehousesService;
import com.example.warehouse.validation.OnCreate;
import com.example.warehouse.validation.OnUpdate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<?> getAllWarehouses(
            @ModelAttribute WarehousesSearchFilters filters,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "25") Integer size,
            @RequestParam(defaultValue = "false") boolean all
    ) {
        try {
            Pageable pageable;
            if (all) {
                pageable = Pageable.unpaged();
            } else {
                pageable = PageRequest.of(page, size);
            }
            return ResponseEntity.ok(warehousesService.getAllWarehouses(filters, pageable));
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

    @PutMapping("/{warehouseId}")
    public ResponseEntity<?> updateWarehouse(@Validated(OnUpdate.class) @RequestBody WarehouseModifyDto warehouseDto,
                                             @PathVariable Integer warehouseId) {
        try {
            Warehouse warehouse = warehousesService.updateWarehouse(warehouseDto, warehouseId);
            return ResponseEntity.ok(warehousesMapper.mapToDto(warehouse));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Warehouse not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @DeleteMapping({"/{warehouseId}"})
    public ResponseEntity<?> deleteWarehouse(@PathVariable Integer warehouseId) {
        try {
            Warehouse warehouse = warehousesService.deleteWarehouse(warehouseId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(warehousesMapper.mapToDto(warehouse));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Warehouse not found: " + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete delete warehouse because other table use this warehouse: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }
}
