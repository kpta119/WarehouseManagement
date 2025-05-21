package com.example.warehouse.controllers;

import com.example.warehouse.domain.Supplier;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.mappers.BusinessEntityMapper;
import com.example.warehouse.services.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    private final BusinessEntityMapper businessEntityMapper;
    private final SupplierService supplierService;

    public SupplierController(BusinessEntityMapper businessEntityMapper, SupplierService supplierService) {
        this.businessEntityMapper = businessEntityMapper;
        this.supplierService = supplierService;
    }

    @PostMapping()
    public ResponseEntity<?> createSupplier(@Valid @RequestBody BusinessEntityDto request, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            fieldError -> Objects.toString(fieldError.getDefaultMessage(), "")
                    ));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            Supplier savedSupplier = supplierService.createSupplier(request);
            BusinessEntityDto responseDto = businessEntityMapper.mapToDto(savedSupplier);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }
}
