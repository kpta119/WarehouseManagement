package com.example.warehouse.services.impl;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.domain.TransactionProduct;
import com.example.warehouse.domain.dto.productDtos.ProductInfoDto;
import com.example.warehouse.domain.dto.transactionDtos.TransactionWithProductsDto;
import com.example.warehouse.mappers.TransactionMapper;
import com.example.warehouse.services.HistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    private final TransactionMapper transactionMapper;

    public HistoryServiceImpl(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    @Override
    public List<TransactionWithProductsDto> getTransactionHistory(List<Transaction> transactions) {
        return transactions.stream()
                .map(t -> {
                    List<ProductInfoDto> products = getProductsFromTransactions(t.getProducts());
                    int totalItems = products.stream().mapToInt(ProductInfoDto::getQuantity).sum();
                    double totalPrice = products.stream().mapToDouble(p -> p.getUnitPrice() * p.getQuantity()).sum();
                    return transactionMapper.mapToDto(t, products, totalItems, totalPrice);
                }).toList();
    }

    @Override
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
