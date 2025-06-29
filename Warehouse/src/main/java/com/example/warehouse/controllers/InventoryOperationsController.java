package com.example.warehouse.controllers;

import com.example.warehouse.domain.dto.InventoryOperationsDtos.ReceiveDeliveryDto;
import com.example.warehouse.domain.dto.InventoryOperationsDtos.SellToClientDto;
import com.example.warehouse.domain.dto.InventoryOperationsDtos.TransferBetweenDto;
import com.example.warehouse.services.InventoryOperationsService;
import com.example.warehouse.validation.OnCreate;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryOperationsController {

    private final InventoryOperationsService inventoryOperationsService;

    public InventoryOperationsController(InventoryOperationsService inventoryOperationsService) {
        this.inventoryOperationsService = inventoryOperationsService;
    }

    @PostMapping("/receive")
    public ResponseEntity<?> receiveDelivery(@Validated(OnCreate.class) @RequestBody ReceiveDeliveryDto receiveTransferDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(inventoryOperationsService.receiveDelivery(receiveTransferDto));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferBetweenWarehouses(@Validated(OnCreate.class) @RequestBody TransferBetweenDto transferDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(inventoryOperationsService.transferBetweenWarehouses(transferDto));
        } catch (NonTransientDataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }

    @PostMapping("/delivery")
    public ResponseEntity<?> sellToClient(@Validated(OnCreate.class) @RequestBody SellToClientDto transferDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(inventoryOperationsService.sellToClient(transferDto));
        } catch (NonTransientDataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }


}
