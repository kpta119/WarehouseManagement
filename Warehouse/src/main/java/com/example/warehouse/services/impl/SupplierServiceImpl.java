package com.example.warehouse.services.impl;

import com.example.warehouse.domain.Address;
import com.example.warehouse.domain.Supplier;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.SupplierDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.SupplierSummaryDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.SupplierWithHistoryDto;
import com.example.warehouse.domain.dto.filtersDto.SupplierSearchFilter;
import com.example.warehouse.domain.dto.transactionDtos.TransactionWithProductsDto;
import com.example.warehouse.mappers.BusinessEntityMapper;
import com.example.warehouse.mappers.BusinessEntityWithHistoryMapper;
import com.example.warehouse.repositories.SupplierRepository;
import com.example.warehouse.services.AddressService;
import com.example.warehouse.services.HistoryService;
import com.example.warehouse.services.SupplierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final BusinessEntityWithHistoryMapper businessEntityWithHistoryMapper;
    private final HistoryService historyService;
    private final AddressService addressService;
    private final BusinessEntityMapper businessEntityMapper;

    public SupplierServiceImpl(
            AddressService addressService,
            SupplierRepository supplierRepository,
            BusinessEntityWithHistoryMapper businessEntityWithHistoryMapper,
            HistoryService historyService,
            BusinessEntityMapper businessEntityMapper
    ) {
        this.supplierRepository = supplierRepository;
        this.businessEntityWithHistoryMapper = businessEntityWithHistoryMapper;
        this.historyService = historyService;
        this.addressService = addressService;
        this.businessEntityMapper = businessEntityMapper;
    }

    @Override
    public SupplierDto createSupplier(BusinessEntityDto request) {
        Address newAddress = addressService.createAddress(request.getAddress());

        Supplier supplier = new Supplier();
        supplier.setName(request.getName());
        supplier.setEmail(request.getEmail());
        supplier.setPhoneNumber(request.getPhoneNumber());
        supplier.setAddress(newAddress);

        Supplier newSupplier = supplierRepository.save(supplier);
        return businessEntityMapper.mapToDto(newSupplier);
    }

    @Override
    public Page<SupplierSummaryDto> getSuppliersWithTransactionCount(SupplierSearchFilter filter, Pageable pageable) {
        return supplierRepository.findAllSuppliersWithTransactionCounts(filter, pageable);
    }

    @Override
    public SupplierWithHistoryDto getSupplierWithHistory(Integer supplierId) {
        Supplier supplier = supplierRepository.findSupplierWithHistoryById(supplierId)
                .orElseThrow(() -> new NoSuchElementException("Supplier not found"));
        List<TransactionWithProductsDto> transactions = historyService.getTransactionsHistory(supplier.getTransactions());
        return businessEntityWithHistoryMapper.mapToDto(supplier, transactions);
    }
}
