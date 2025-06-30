package com.example.warehouse.controllers;

import com.example.warehouse.domain.Address;
import com.example.warehouse.dtos.addressDtos.AddressDto;
import com.example.warehouse.dtos.addressDtos.AddressInfoDto;
import com.example.warehouse.mappers.AddressMapper;
import com.example.warehouse.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    private final AddressService addressService;
    private final AddressMapper addressMapper;

    public AddressController(AddressService addressService, AddressMapper addressMapper) {
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    @PostMapping()
    public ResponseEntity<?> createAddress(@Valid @RequestBody AddressInfoDto request) {
        try {
            Address savedAddress = addressService.createAddress(request);
            AddressDto response = addressMapper.mapToDto(savedAddress);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }
}
