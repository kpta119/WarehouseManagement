package com.example.warehouse.services;

import com.example.warehouse.domain.Transaction;

public interface TransactionService {

    Transaction getTransactionById(Integer transactionId);
}
