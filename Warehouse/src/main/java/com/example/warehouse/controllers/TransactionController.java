package com.example.warehouse.controllers;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.domain.dto.TransactionDto;
import com.example.warehouse.domain.dto.TransactionSummaryDto;
import com.example.warehouse.mappers.TransactionMapper;
import com.example.warehouse.mappers.TransactionSummaryMapper;
import com.example.warehouse.services.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {
    private TransactionService transactionService;
    private TransactionMapper transactionMapper;
    private TransactionSummaryMapper transactionSummaryMapper;

    public TransactionController(TransactionMapper transactionMapper, TransactionService transactionService, TransactionSummaryMapper transactionSummaryMapper) {
        this.transactionMapper = transactionMapper;
        this.transactionService = transactionService;
        this.transactionSummaryMapper = transactionSummaryMapper;
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDto> getTransaction(@PathVariable Integer transactionId){
        try {
            Transaction transaction = transactionService.getTransactionById(transactionId);
            return ResponseEntity.ok(transactionMapper.mapToDto(transaction));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<TransactionSummaryDto>> getAllTransactions(
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
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
