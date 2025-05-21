package com.example.warehouse.controllers;

import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.ClientSummaryDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.ClientWithHistoryDto;
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
import java.util.Objects;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> getAllClients(){
        try{
            List<ClientSummaryDto> allClients = clientService.getClientsWithTransactionCount();
            return ResponseEntity.status(HttpStatus.OK).body(allClients);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<?> getClientWithHistory(@PathVariable("clientId") Integer clientId){
        try{
            ClientWithHistoryDto clientWithHistoryDto = clientService.getClientWithHistory(clientId);
            return ResponseEntity.status(HttpStatus.OK).body(clientWithHistoryDto);
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
                            fieldError -> Objects.toString(fieldError.getDefaultMessage(), "")
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

