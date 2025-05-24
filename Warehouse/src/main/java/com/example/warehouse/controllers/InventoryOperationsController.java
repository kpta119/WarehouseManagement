package com.example.warehouse.controllers;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.domain.dto.InventoryOperationsDtos.ReceiveDeliveryDto;
import com.example.warehouse.domain.dto.InventoryOperationsDtos.TransferBetweenDto;
import com.example.warehouse.mappers.InventoryOperationsMapper;
import com.example.warehouse.services.InventoryOperationsService;
import com.example.warehouse.validation.OnCreate;
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
    private final InventoryOperationsMapper inventoryOperationsMapper;

    public InventoryOperationsController(InventoryOperationsService inventoryOperationsService,
                                         InventoryOperationsMapper inventoryOperationsMapper) {
        this.inventoryOperationsService = inventoryOperationsService;
        this.inventoryOperationsMapper = inventoryOperationsMapper;
    }

    @PostMapping("/receive")
    public ResponseEntity<?> receiveDelivery(@Validated(OnCreate.class) @RequestBody ReceiveDeliveryDto receiveTransferDto) {
        try {
            Transaction transaction = inventoryOperationsService.receiveDelivery(receiveTransferDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(inventoryOperationsMapper.mapToDto(transaction));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferBetweenWarehouses(@Validated(OnCreate.class) @RequestBody TransferBetweenDto transferDto) {
        try {
            Transaction transaction = inventoryOperationsService.transferBetweenWarehouses(transferDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(inventoryOperationsMapper.mapToDto(transaction));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }


}
