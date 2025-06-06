package com.example.warehouse.mappers;

import com.example.warehouse.domain.Address;
import com.example.warehouse.domain.City;
import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.Supplier;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.ClientSummaryDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.SupplierSummaryDto;
import org.springframework.stereotype.Component;

@Component
public class BusinessEntitySummaryMapper {
    public ClientSummaryDto mapToClientDto(Object[] queryResult) {
        Client client = (Client) queryResult[0];
        Long transactionFromDb = (Long) queryResult[1];
        Integer transactionCount = transactionFromDb.intValue();

        ClientSummaryDto dto = new ClientSummaryDto();
        dto.setClientId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setPhoneNumber(client.getPhoneNumber());
        Address address = client.getAddress();
        City city = address.getCity();
        dto.setAddress(
                address.getStreet() + " " + address.getStreetNumber() + " " + city.getName() + ", " + city.getCountry().getName()
        );
        dto.setTransactionsCount(transactionCount);
        return dto;
    }

    public SupplierSummaryDto mapToSupplierDto(Object[] queryResult) {
        Supplier supplier = (Supplier) queryResult[0];
        Long transactionFromDb = (Long) queryResult[1];
        Integer transactionCount = transactionFromDb.intValue();

        SupplierSummaryDto dto = new SupplierSummaryDto();
        dto.setSupplierId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setEmail(supplier.getEmail());
        dto.setPhoneNumber(supplier.getPhoneNumber());
        Address address = supplier.getAddress();
        City city = address.getCity();
        dto.setAddress(
                address.getStreet() + " " + address.getStreetNumber() + " " + city.getName() + ", " + city.getCountry().getName()
        );
        dto.setTransactionsCount(transactionCount);
        return dto;
    }
}
