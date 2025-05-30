package com.example.warehouse.mappers;

import com.example.warehouse.domain.Address;
import com.example.warehouse.domain.City;
import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.Supplier;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.ClientWithHistoryDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.SupplierWithHistoryDto;
import com.example.warehouse.domain.dto.transactionDtos.TransactionWithProductsDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BusinesEntityWithHistoryMapper {

    private final TransactionMapper transactionMapper;

    public BusinesEntityWithHistoryMapper(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    public ClientWithHistoryDto mapToDto(Client client) {
        ClientWithHistoryDto dto = new ClientWithHistoryDto();
        dto.setClientId(client.getId());
        Address address = client.getAddress();
        City city = address.getCity();
        dto.setAddress(
                address.getStreet() + " " + address.getStreetNumber() + " " + city.getName() + ", " + city.getCountry().getName()
        );
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


    public SupplierWithHistoryDto mapToDto(Supplier supplier) {
        SupplierWithHistoryDto dto = new SupplierWithHistoryDto();
        dto.setSupplierId(supplier.getId());
        Address address = supplier.getAddress();
        City city = address.getCity();
        dto.setAddress(
                address.getStreet() + " " + address.getStreetNumber() + " " + city.getName() + ", " + city.getCountry().getName()
        );
        dto.setEmail(supplier.getEmail());
        dto.setName(supplier.getName());
        dto.setPhoneNumber(supplier.getPhoneNumber());
        List<TransactionWithProductsDto> listOfTransactions = supplier.getTransactions()
                .stream()
                .map(transactionMapper::mapToDto)
                .collect(Collectors.toList());
        dto.setHistory(listOfTransactions);
        return dto;
    }
}
