package com.example.warehouse.controllers;

import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.dto.ClientDto;
import com.example.warehouse.mappers.ClientMapper;
import com.example.warehouse.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private ClientMapper clientMapper;
    private ClientService clientService;

    public ClientController(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @PostMapping()
    public ResponseEntity<?> createClient(@RequestBody ClientDto request) {
        try {
            Client savedClient = clientService.createClient(request);
            ClientDto responseDto = clientMapper.mapToDto(savedClient);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }


}
