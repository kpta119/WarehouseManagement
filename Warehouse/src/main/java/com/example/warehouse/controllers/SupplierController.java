package com.example.warehouse.controllers;

import com.example.warehouse.dtos.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.dtos.filtersDto.SupplierSearchFilter;
import com.example.warehouse.services.SupplierService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllSuppliers(
            @ModelAttribute SupplierSearchFilter filters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "false") boolean all
    ) {
        try {
            Pageable pageable;
            if (all) {
                pageable = Pageable.unpaged();
            } else {
                pageable = PageRequest.of(page, size);
            }
            return ResponseEntity.status(HttpStatus.OK).body(supplierService.getSuppliersWithTransactionCount(filters, pageable));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<?> getClientWithHistory(@PathVariable("supplierId") Integer supplierId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(supplierService.getSupplierWithHistory(supplierId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }


    @PostMapping()
    public ResponseEntity<?> createSupplier(@Valid @RequestBody BusinessEntityDto request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(supplierService.createSupplier(request));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }
}
