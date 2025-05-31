package com.example.warehouse.services;

import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.ClientSummaryDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.ClientWithHistoryDto;
import com.example.warehouse.domain.dto.filtersDto.ClientSearchFilters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {
    Client createClient(BusinessEntityDto request);

    Page<ClientSummaryDto> getClientsWithTransactionCount(ClientSearchFilters filters, Pageable pageable);

    ClientWithHistoryDto getClientWithHistory(Integer clientId);
}
