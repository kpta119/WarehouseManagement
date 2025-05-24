package com.example.warehouse.services;

import com.example.warehouse.domain.Supplier;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.SupplierSummaryDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.SupplierWithHistoryDto;

import java.util.List;

public interface SupplierService {
    Supplier createSupplier(BusinessEntityDto request);

    List<SupplierSummaryDto> getSuppliersWithTransactionCount();

    SupplierWithHistoryDto getSupplierWithHistory(Integer supplierId);
}
