package com.example.warehouse.services;

import com.example.warehouse.domain.Transaction;

import java.util.Date;
import java.util.List;

public interface TransactionService {

    Transaction getTransactionById(Integer transactionId);

    List<Transaction> getAllTransactions(Date fromDate, Date toDate, Transaction.TransactionType transactionType, Integer employeeId);
}
