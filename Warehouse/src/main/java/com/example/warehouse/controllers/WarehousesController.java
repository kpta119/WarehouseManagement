package com.example.warehouse.controllers;

import com.example.warehouse.domain.dto.filtersDto.WarehousesSearchFilters;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseModifyDto;
import com.example.warehouse.services.WarehousesService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/warehouses")
public class WarehousesController {

    private final WarehousesService warehousesService;

    public WarehousesController(WarehousesService warehousesService) {
        this.warehousesService = warehousesService;
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
    public ResponseEntity<?> createWarehouse(@Valid @RequestBody WarehouseModifyDto warehouseDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(warehousesService.createWarehouse(warehouseDto));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @PutMapping("/{warehouseId}")
    public ResponseEntity<?> updateWarehouse(@Valid @RequestBody WarehouseModifyDto warehouseDto,
                                             @PathVariable Integer warehouseId) {
        try {
            return ResponseEntity.ok(warehousesService.updateWarehouse(warehouseDto, warehouseId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Warehouse not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @DeleteMapping({"/{warehouseId}"})
    public ResponseEntity<?> deleteWarehouse(@PathVariable Integer warehouseId) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(warehousesService.deleteWarehouse(warehouseId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Warehouse not found: " + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete delete warehouse because other table use this warehouse: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }
}
