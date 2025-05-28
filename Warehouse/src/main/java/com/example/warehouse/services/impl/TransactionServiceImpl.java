package com.example.warehouse.services.impl;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.repositories.TransactionRepository;
import com.example.warehouse.services.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;


@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction getTransactionById(Integer transactionId) {
        return transactionRepository.findById(transactionId).
                orElseThrow(() -> new NoSuchElementException("Transaction not found with ID: " + transactionId));
    }

    @Override
    public Page<Transaction> getAllTransactions(Double minTotalPrice, Double maxTotalPrice, Double minTotalSize, Double maxTotalSize, Date fromDate, Date toDate, Transaction.TransactionType type, Integer employeeId, Pageable pageable) {
        return transactionRepository.findAllWithFilters(
                minTotalPrice,
                maxTotalPrice,
                minTotalSize,
                maxTotalSize,
                startOfDay(fromDate),
                toDate,
                type,
                employeeId,
                pageable);
    }

    private Date startOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
