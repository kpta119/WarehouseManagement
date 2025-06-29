package com.example.warehouse.services;

import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.SupplierDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.SupplierSummaryDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.SupplierWithHistoryDto;
import com.example.warehouse.domain.dto.filtersDto.SupplierSearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SupplierService {
    SupplierDto createSupplier(BusinessEntityDto request);

    Page<SupplierSummaryDto> getSuppliersWithTransactionCount(SupplierSearchFilter filter, Pageable pageable);

    SupplierWithHistoryDto getSupplierWithHistory(Integer supplierId);
}
