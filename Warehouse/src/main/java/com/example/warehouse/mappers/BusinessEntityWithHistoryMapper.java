package com.example.warehouse.mappers;

import com.example.warehouse.domain.*;
import com.example.warehouse.dtos.clientAndSupplierDtos.ClientWithHistoryDto;
import com.example.warehouse.dtos.clientAndSupplierDtos.BusinessEntityWithHistoryDto;
import com.example.warehouse.dtos.clientAndSupplierDtos.SupplierWithHistoryDto;
import com.example.warehouse.dtos.transactionDtos.TransactionWithProductsDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BusinessEntityWithHistoryMapper {

    public BusinessEntityWithHistoryMapper() {}

    public ClientWithHistoryDto mapToDto(Client client, List<TransactionWithProductsDto> transactions) {
        ClientWithHistoryDto dto = new ClientWithHistoryDto();
        mapToDto(dto, client, transactions);
        return dto;
    }

    public SupplierWithHistoryDto mapToDto(Supplier supplier, List<TransactionWithProductsDto> transactions) {
        SupplierWithHistoryDto dto = new SupplierWithHistoryDto();
        mapToDto(dto, supplier, transactions);
        return dto;
    }

    public void mapToDto(BusinessEntityWithHistoryDto part, BusinessEntity dataSource, List<TransactionWithProductsDto> transactions) {
        part.setId(dataSource.getId());
        Address address = dataSource.getAddress();
        City city = address.getCity();
        part.setAddress(
                address.getStreet() + " " + address.getStreetNumber() + " " + city.getName() + ", " + city.getCountry().getName()
        );
        part.setEmail(dataSource.getEmail());
        part.setName(dataSource.getName());
        part.setPhoneNumber(dataSource.getPhoneNumber());
        part.setHistory(transactions);
    }
}
