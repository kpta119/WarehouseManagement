package com.example.warehouse.services;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.domain.dto.filtersDto.TransactionsSearchFilters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    Transaction getTransactionById(Integer transactionId);

    Page<Object[]> getAllTransactions(TransactionsSearchFilters filters, Pageable pageable);
}
