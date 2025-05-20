package com.example.warehouse.services;

import com.example.warehouse.domain.Supplier;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;

public interface SupplierService {
    public Supplier createSupplier(BusinessEntityDto request);
}
