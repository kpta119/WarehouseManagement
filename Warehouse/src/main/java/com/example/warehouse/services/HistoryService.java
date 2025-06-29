package com.example.warehouse.services;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.domain.TransactionProduct;
import com.example.warehouse.domain.dto.productDtos.ProductInfoDto;
import com.example.warehouse.domain.dto.transactionDtos.TransactionWithProductsDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HistoryService {
    List<TransactionWithProductsDto> getTransactionHistory(List<Transaction> transactions);

    List<ProductInfoDto> getProductsFromTransactions(List<TransactionProduct> transactionProducts);
}
