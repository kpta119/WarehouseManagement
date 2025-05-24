package com.example.warehouse.services;

import com.example.warehouse.domain.Address;
import com.example.warehouse.domain.dto.addressDtos.AddressInfoDto;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {
    Address createAddress(AddressInfoDto addressDto);
}
