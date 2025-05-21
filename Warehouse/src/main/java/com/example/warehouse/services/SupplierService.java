package com.example.warehouse.services;

import com.example.warehouse.domain.Supplier;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.SupplierSummaryDto;

import java.util.List;

public interface SupplierService {
    public Supplier createSupplier(BusinessEntityDto request);

    public List<SupplierSummaryDto> getSuppliersWithTransactionCount();
}
