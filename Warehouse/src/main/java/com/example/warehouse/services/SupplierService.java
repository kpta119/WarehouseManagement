package com.example.warehouse.services;

import com.example.warehouse.domain.Supplier;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.SupplierSummaryDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.SupplierWithHistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SupplierService {
    Supplier createSupplier(BusinessEntityDto request);

    Page<SupplierSummaryDto> getSuppliersWithTransactionCount(String regionName, Integer minTransactions, Integer maxTransactions, Integer warehouseId, Pageable pageable);

    SupplierWithHistoryDto getSupplierWithHistory(Integer supplierId);
}
