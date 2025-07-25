package com.example.warehouse.services;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.dtos.filtersDto.TransactionsSearchFilters;
import com.example.warehouse.dtos.transactionDtos.TransactionSummaryDto;
import com.example.warehouse.dtos.transactionDtos.TransactionWithProductsDto;
import com.example.warehouse.repositories.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final HistoryService historyService;

    public TransactionService(TransactionRepository transactionRepository, HistoryService historyService) {
        this.transactionRepository = transactionRepository;
        this.historyService = historyService;
    }

    public TransactionWithProductsDto getTransactionById(Integer transactionId) {
        Transaction transaction =  transactionRepository.findTransactionHistoryById(transactionId).
                orElseThrow(() -> new NoSuchElementException("Transaction not found with ID: " + transactionId));
        return historyService.getTransactionHistory(transaction);
    }

    public Page<TransactionSummaryDto> getAllTransactions(TransactionsSearchFilters filters, Pageable pageable) {
        Date normalizedFromDate = (filters.getFromDate() != null) ? startOfDay(filters.getFromDate()) : null;
        return transactionRepository.findAllWithFilters(filters, normalizedFromDate, pageable);
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
