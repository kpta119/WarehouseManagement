package com.example.warehouse.services.impl;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.repositories.TransactionRepository;
import com.example.warehouse.services.TransactionService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction getTransactionById(Integer transactionId) {
        return transactionRepository.findById(transactionId).
                orElseThrow(() -> new NoSuchElementException("Transaction not found with ID: " + transactionId));
    }
}
