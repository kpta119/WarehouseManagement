package com.example.warehouse.mappers.impl;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.domain.dto.TransactionDto;
import com.example.warehouse.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapperImpl implements Mapper<Transaction, TransactionDto> {
    private ModelMapper modelMapper;

    public TransactionMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TransactionDto mapTo(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDto.class);
    }

    @Override
    public Transaction mapFrom(TransactionDto transactionDto) {
        return modelMapper.map(transactionDto, Transaction.class);
    }
}
