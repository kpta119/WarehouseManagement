package com.example.warehouse.services;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.domain.TransactionProduct;
import com.example.warehouse.domain.dto.productDtos.ProductInfoDto;
import com.example.warehouse.domain.dto.transactionDtos.TransactionWithProductsDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HistoryService {
    List<TransactionWithProductsDto> getTransactionsHistory(List<Transaction> transactions);

    TransactionWithProductsDto getTransactionHistory(Transaction transaction);

    List<ProductInfoDto> getProductsFromTransactions(List<TransactionProduct> transactionProducts);
}
