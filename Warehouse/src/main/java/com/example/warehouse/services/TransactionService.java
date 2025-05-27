package com.example.warehouse.services;

import com.example.warehouse.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface TransactionService {

    Transaction getTransactionById(Integer transactionId);

    Page<Transaction> getAllTransactions(Date fromDate, Date toDate, Transaction.TransactionType transactionType, Integer employeeId, Pageable pageable);
}
