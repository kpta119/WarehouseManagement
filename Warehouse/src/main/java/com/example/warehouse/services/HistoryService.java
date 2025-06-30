package com.example.warehouse.services;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.domain.TransactionProduct;
import com.example.warehouse.dtos.productDtos.ProductInfoDto;
import com.example.warehouse.dtos.transactionDtos.TransactionWithProductsDto;
import com.example.warehouse.mappers.TransactionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {
    private final TransactionMapper transactionMapper;

    public HistoryService(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    public List<TransactionWithProductsDto> getTransactionsHistory(List<Transaction> transactions) {
        return transactions.stream()
                .map(this::getTransactionHistory).toList();
    }

    public TransactionWithProductsDto getTransactionHistory(Transaction transaction) {
        List<ProductInfoDto> products = getProductsFromTransactions(transaction.getProducts());
        int totalItems = products.stream().mapToInt(ProductInfoDto::getQuantity).sum();
        double totalPrice = products.stream().mapToDouble(p -> p.getUnitPrice() * p.getQuantity()).sum();
        return transactionMapper.mapToDto(transaction, products, totalItems, totalPrice);
    }

    public List<ProductInfoDto> getProductsFromTransactions(List<TransactionProduct> transactionProducts) {
        return transactionProducts.stream()
                .map(tp -> {
                    ProductInfoDto pDto = new ProductInfoDto();
                    pDto.setProductId(tp.getProduct().getId());
                    pDto.setName(tp.getProduct().getName());
                    pDto.setQuantity(tp.getQuantity());
                    pDto.setUnitPrice(tp.getTransactionPrice());
                    pDto.setCategoryName(tp.getProduct().getCategory().getName());
                    return pDto;
                }).toList();
    }
}
