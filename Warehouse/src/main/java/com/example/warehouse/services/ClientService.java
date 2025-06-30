package com.example.warehouse.services;

import com.example.warehouse.domain.Address;
import com.example.warehouse.domain.Client;
import com.example.warehouse.dtos.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.dtos.clientAndSupplierDtos.ClientDto;
import com.example.warehouse.dtos.clientAndSupplierDtos.ClientSummaryDto;
import com.example.warehouse.dtos.clientAndSupplierDtos.ClientWithHistoryDto;
import com.example.warehouse.dtos.filtersDto.ClientSearchFilters;
import com.example.warehouse.dtos.transactionDtos.TransactionWithProductsDto;
import com.example.warehouse.mappers.BusinessEntityMapper;
import com.example.warehouse.mappers.BusinessEntityWithHistoryMapper;
import com.example.warehouse.repositories.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final BusinessEntityWithHistoryMapper businessEntityWithHistoryMapper;
    private final HistoryService historyService;
    private final AddressService addressService;
    private final BusinessEntityMapper businessEntityMapper;


    public ClientService(
            ClientRepository clientRepository,
            BusinessEntityWithHistoryMapper businessEntityWithHistoryMapper,
            HistoryService historyService,
            AddressService addressService,
            BusinessEntityMapper businessEntityMapper
    ) {
        this.clientRepository = clientRepository;
        this.businessEntityWithHistoryMapper = businessEntityWithHistoryMapper;
        this.historyService = historyService;
        this.addressService = addressService;
        this.businessEntityMapper = businessEntityMapper;
    }

    public ClientDto createClient(BusinessEntityDto request) {
        Address newAddress = addressService.createAddress(request.getAddress());

        Client client = new Client();
        client.setName(request.getName());
        client.setEmail(request.getEmail());
        client.setPhoneNumber(request.getPhoneNumber());
        client.setAddress(newAddress);

        Client newClient = clientRepository.save(client);
        return businessEntityMapper.mapToDto(newClient);
    }

    public Page<ClientSummaryDto> getClientsWithTransactionCount(ClientSearchFilters filters, Pageable pageable){
        return clientRepository.findAllClientsWithTransactionCounts(filters, pageable);
    }

    public ClientWithHistoryDto getClientWithHistory(Integer clientId) {
        Client client = clientRepository.findClientWithHistoryById(clientId)
                .orElseThrow(() -> new NoSuchElementException("Client not found"));
        List<TransactionWithProductsDto> transactions = historyService.getTransactionsHistory(client.getTransactions());
        return businessEntityWithHistoryMapper.mapToDto(client, transactions);
    }
}
