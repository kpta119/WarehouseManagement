package com.example.warehouse.mappers;

import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityWithHistoryDto;
import com.example.warehouse.domain.dto.transactionDtos.TransactionWithProductsDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BusinesEntityWithHistoryMapper {

    private final TransactionMapper transactionMapper;
    private final AddressMapper addressMapper;

    public BusinesEntityWithHistoryMapper(TransactionMapper transactionMapper, AddressMapper addressMapper) {
        this.transactionMapper = transactionMapper;
        this.addressMapper = addressMapper;
    }

    public BusinessEntityWithHistoryDto mapToDto(Client client) {
        BusinessEntityWithHistoryDto dto = new BusinessEntityWithHistoryDto();
        dto.setClientId(client.getId());
        dto.setAddress(addressMapper.mapToDto(client.getAddress()));
        dto.setEmail(client.getEmail());
        dto.setName(client.getName());
        dto.setPhoneNumber(client.getPhoneNumber());
        List<TransactionWithProductsDto> listOfTransactions = client.getTransactions()
                .stream()
                .map(transactionMapper::mapToDto)
                .collect(Collectors.toList());
        dto.setHistory(listOfTransactions);
        return dto;
    }
}
