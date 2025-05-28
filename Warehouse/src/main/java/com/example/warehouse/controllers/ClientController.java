package com.example.warehouse.controllers;

import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.ClientSummaryDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.ClientWithHistoryDto;
import com.example.warehouse.mappers.BusinessEntityMapper;
import com.example.warehouse.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final BusinessEntityMapper businessEntityMapper;
    private final ClientService clientService;

    public ClientController(ClientService clientService, BusinessEntityMapper businessEntityMapper) {
        this.clientService = clientService;
        this.businessEntityMapper = businessEntityMapper;
    }

    @GetMapping()
    public ResponseEntity<?> getAllClients(
            @RequestParam(required = false) String regionName,
            @RequestParam(required = false) Integer minTransactions,
            @RequestParam(required = false) Integer maxTransactions,
            @RequestParam(required = false) Integer warehouseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {
        try {
            Page<ClientSummaryDto> response = clientService.getClientsWithTransactionCount(regionName, minTransactions, maxTransactions, warehouseId, PageRequest.of(page, size));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<?> getClientWithHistory(@PathVariable("clientId") Integer clientId) {
        try {
            ClientWithHistoryDto clientWithHistoryDto = clientService.getClientWithHistory(clientId);
            return ResponseEntity.status(HttpStatus.OK).body(clientWithHistoryDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createClient(@Valid @RequestBody BusinessEntityDto request) {
        try {
            Client savedClient = clientService.createClient(request);
            BusinessEntityDto responseDto = businessEntityMapper.mapToDto(savedClient);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }
}

