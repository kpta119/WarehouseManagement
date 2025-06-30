package com.example.warehouse.controllers;

import com.example.warehouse.domain.dto.filtersDto.TransactionsSearchFilters;
import com.example.warehouse.services.TransactionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransaction(@PathVariable Integer transactionId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(transactionService.getTransactionById(transactionId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllTransactions(
            @ModelAttribute TransactionsSearchFilters filters,
            @RequestParam(defaultValue = "false") boolean all,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size
    ) {
        try {
            Pageable pageable;
            if (all) {
                pageable = Pageable.unpaged();
            } else {
                pageable = PageRequest.of(page, size);
            }
            return ResponseEntity.status(HttpStatus.OK).body(transactionService.getAllTransactions(filters, pageable));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }
}
