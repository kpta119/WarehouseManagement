package com.example.warehouse.controllers;

import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntitySummaryDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityWithHistoryDto;
import com.example.warehouse.mappers.BusinessEntityMapper;
import com.example.warehouse.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private BusinessEntityMapper businessEntityMapper;
    private ClientService clientService;

    public ClientController(ClientService clientService, BusinessEntityMapper businessEntityMapper) {
        this.clientService = clientService;
        this.businessEntityMapper = businessEntityMapper;
    }

    @GetMapping()
    public ResponseEntity<?> getAllClients(){
        try{
            List<BusinessEntitySummaryDto> allClients = clientService.getClientsWithTransactionCount();
            return ResponseEntity.status(HttpStatus.OK).body(allClients);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<?> getClientWithHistory(@PathVariable("clientId") Integer clientId){
        try{
            BusinessEntityWithHistoryDto businessEntityWithHistoryDto = clientService.getClientWithHistory(clientId);
            return ResponseEntity.status(HttpStatus.OK).body(businessEntityWithHistoryDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createClient(@Valid @RequestBody BusinessEntityDto request, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            FieldError::getDefaultMessage
                    ));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
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

