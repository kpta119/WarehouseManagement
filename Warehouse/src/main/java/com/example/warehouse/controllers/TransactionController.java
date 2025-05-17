package com.example.warehouse.controllers;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.domain.dto.TransactionDto;
import com.example.warehouse.mappers.Mapper;
import com.example.warehouse.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {
    private TransactionService transactionService;

    private Mapper<Transaction, TransactionDto> transactionMapper;

    public TransactionController(Mapper<Transaction, TransactionDto> transactionMapper, TransactionService transactionService) {
        this.transactionMapper = transactionMapper;
        this.transactionService = transactionService;
    }

    @PostMapping("/{transactionId}")
    public ResponseEntity<TransactionDto> getTransaction(@PathVariable Integer transactionId){
        try {
            Transaction transaction = transactionService.getTransactionById(transactionId);
            return ResponseEntity.ok(transactionMapper.mapTo(transaction));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
