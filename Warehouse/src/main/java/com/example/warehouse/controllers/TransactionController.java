package com.example.warehouse.controllers;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.domain.dto.TransactionDtos.TransactionSummaryDto;
import com.example.warehouse.mappers.TransactionMapper;
import com.example.warehouse.mappers.TransactionSummaryMapper;
import com.example.warehouse.services.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    private final TransactionSummaryMapper transactionSummaryMapper;

    public TransactionController(TransactionMapper transactionMapper, TransactionService transactionService, TransactionSummaryMapper transactionSummaryMapper) {
        this.transactionMapper = transactionMapper;
        this.transactionService = transactionService;
        this.transactionSummaryMapper = transactionSummaryMapper;
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransaction(@PathVariable Integer transactionId){
        try {
            Transaction transaction = transactionService.getTransactionById(transactionId);
            return ResponseEntity.ok(transactionMapper.mapToDto(transaction));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllTransactions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate,
            @RequestParam(required = false) Transaction.TransactionType type,
            @RequestParam(required = false) Integer employeeId
    ){
        try{
            List<Transaction> transactions = transactionService.getAllTransactions(fromDate, toDate, type, employeeId);
            List<TransactionSummaryDto> dtos = transactions.stream()
                    .map(transactionSummaryMapper::mapToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }
}
