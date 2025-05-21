package com.example.warehouse.services;

import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.ClientSummaryDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.ClientWithHistoryDto;

import java.util.List;

public interface ClientService {
    public Client createClient(BusinessEntityDto request);

    public List<ClientSummaryDto> getClientsWithTransactionCount();

    public ClientWithHistoryDto getClientWithHistory(Integer clientId);
}
