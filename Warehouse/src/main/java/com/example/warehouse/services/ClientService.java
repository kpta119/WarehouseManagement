package com.example.warehouse.services;

import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.dto.ClientDto;

public interface ClientService {
    public Client createClient(ClientDto request);
}
