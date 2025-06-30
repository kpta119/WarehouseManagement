package com.example.warehouse.services;

import com.example.warehouse.domain.dto.filtersDto.TransactionsSearchFilters;
import com.example.warehouse.domain.dto.transactionDtos.TransactionSummaryDto;
import com.example.warehouse.domain.dto.transactionDtos.TransactionWithProductsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    TransactionWithProductsDto getTransactionById(Integer transactionId);

    Page<TransactionSummaryDto> getAllTransactions(TransactionsSearchFilters filters, Pageable pageable);
}
