package com.example.warehouse.services;

import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;

public interface ClientService {
    public Client createClient(BusinessEntityDto request);
}
