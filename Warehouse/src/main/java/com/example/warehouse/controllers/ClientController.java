package com.example.warehouse.controllers;

import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.ClientDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.ClientSummaryDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.ClientWithHistoryDto;
import com.example.warehouse.domain.dto.filtersDto.ClientSearchFilters;
import com.example.warehouse.mappers.BusinessEntityMapper;
import com.example.warehouse.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            @ModelAttribute ClientSearchFilters filters,
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
            Page<ClientSummaryDto> response = clientService.getClientsWithTransactionCount(filters, pageable);
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
            ClientDto responseDto = businessEntityMapper.mapToDto(savedClient);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }
}

