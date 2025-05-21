package com.example.warehouse.services;

import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntitySummaryDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityWithHistoryDto;

import java.util.List;

public interface ClientService {
    public Client createClient(BusinessEntityDto request);

    public List<BusinessEntitySummaryDto> getClientsWithTransactionCount();

    public BusinessEntityWithHistoryDto getClientWithHistory(Integer clientId);
}
