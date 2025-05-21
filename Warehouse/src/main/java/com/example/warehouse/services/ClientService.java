package com.example.warehouse.services;

import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.ClientSummaryDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.ClientWithHistoryDto;

import java.util.List;

public interface ClientService {
    Client createClient(BusinessEntityDto request);

    List<ClientSummaryDto> getClientsWithTransactionCount();

    ClientWithHistoryDto getClientWithHistory(Integer clientId);
}
