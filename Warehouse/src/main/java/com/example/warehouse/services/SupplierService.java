package com.example.warehouse.services;

import com.example.warehouse.domain.Address;
import com.example.warehouse.domain.Supplier;
import com.example.warehouse.dtos.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.dtos.clientAndSupplierDtos.SupplierDto;
import com.example.warehouse.dtos.clientAndSupplierDtos.SupplierSummaryDto;
import com.example.warehouse.dtos.clientAndSupplierDtos.SupplierWithHistoryDto;
import com.example.warehouse.dtos.filtersDto.SupplierSearchFilter;
import com.example.warehouse.dtos.transactionDtos.TransactionWithProductsDto;
import com.example.warehouse.mappers.BusinessEntityMapper;
import com.example.warehouse.mappers.BusinessEntityWithHistoryMapper;
import com.example.warehouse.repositories.SupplierRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final BusinessEntityWithHistoryMapper businessEntityWithHistoryMapper;
    private final HistoryService historyService;
    private final AddressService addressService;
    private final BusinessEntityMapper businessEntityMapper;

    public SupplierService(
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

    public Page<SupplierSummaryDto> getSuppliersWithTransactionCount(SupplierSearchFilter filter, Pageable pageable) {
        return supplierRepository.findAllSuppliersWithTransactionCounts(filter, pageable);
    }

    public SupplierWithHistoryDto getSupplierWithHistory(Integer supplierId) {
        Supplier supplier = supplierRepository.findSupplierWithHistoryById(supplierId)
                .orElseThrow(() -> new NoSuchElementException("Supplier not found"));
        List<TransactionWithProductsDto> transactions = historyService.getTransactionsHistory(supplier.getTransactions());
        return businessEntityWithHistoryMapper.mapToDto(supplier, transactions);
    }
}
