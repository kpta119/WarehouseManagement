package com.example.warehouse.services.impl;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.domain.dto.dashboardDtos.SummaryDto;
import com.example.warehouse.repositories.ProductInventoryRepository;
import com.example.warehouse.repositories.TransactionProductRepository;
import com.example.warehouse.repositories.TransactionRepository;
import com.example.warehouse.repositories.WarehouseRepository;
import com.example.warehouse.services.DashboardService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final TransactionRepository transactionRepository;
    private final TransactionProductRepository transactionProductRepository;
    private final ProductInventoryRepository productInventoryRepository;
    private final WarehouseRepository warehouseRepository;

    public DashboardServiceImpl(TransactionRepository transactionRepository, TransactionProductRepository transactionProductRepository, ProductInventoryRepository productInventoryRepository, WarehouseRepository warehouseRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionProductRepository = transactionProductRepository;
        this.productInventoryRepository = productInventoryRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public SummaryDto getSummary(Integer warehouseId) {
        if (warehouseId != null) {
            if (warehouseRepository.findById(warehouseId).isEmpty()){
                throw new NoSuchElementException("The warehouse with this id: " + warehouseId + " does not exist");
            }
        }
        SummaryDto dto = new SummaryDto();
        dto.setProductsCount(getProductsCount(warehouseId));
        dto.setCategoriesCount(getCategoriesCount(warehouseId));
        dto.setLowStockCount(getLowStockCount(warehouseId));
        dto.setMonthlyReceipts(getMonthlyReceipts(warehouseId));
        dto.setMonthlyDeliveries(getMonthlyDeliveries(warehouseId));
        dto.setTopProduct(getTopProduct(warehouseId));
        dto.setInventoryValue(getInventoryValue(warehouseId));
        dto.setTurnoverLastWeek(getTurnoverLastWeek(warehouseId));
        dto.setLastReceiptDate(getLastReceiptDate(warehouseId));
        dto.setLastDeliveryDate(getLastDeliveryDate(warehouseId));
        return dto;
    }

    private Integer getProductsCount(Integer warehouseId) {
        return productInventoryRepository.countByWarehouse(warehouseId);
    }

    private Integer getCategoriesCount(Integer warehouseId) {
        return productInventoryRepository.countDistinctCategoriesByWarehouse(warehouseId);
    }

    private Integer getLowStockCount(Integer warehouseId) {
        return productInventoryRepository.countLowStockByWarehouse(warehouseId);
    }

    private Integer getMonthlyReceipts(Integer warehouseId) {
        return transactionRepository.countByTypeAndDateBetween(
                Transaction.TransactionType.SUPPLIER_TO_WAREHOUSE,
                getStartDate(),
                LocalDateTime.now(),
                warehouseId
        );
    }

    private Integer getMonthlyDeliveries(Integer warehouseId) {
        return transactionRepository.countByTypeAndDateBetween(
                Transaction.TransactionType.WAREHOUSE_TO_CUSTOMER,
                getStartDate(),
                LocalDateTime.now(),
                warehouseId
        );
    }

    private String getTopProduct(Integer warehouseId) {
        return transactionProductRepository.findTopProduct(warehouseId)
                .map(projection -> projection.getProduct().getName())
                .orElse("No data");
    }

    private Double getInventoryValue(Integer warehouseId) {
        return productInventoryRepository.calculateInventoryValue(warehouseId)
                .orElse(0.0);
    }

    private Double getTurnoverLastWeek(Integer warehouseId) {
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        return transactionProductRepository.calculateTurnoverLastWeek(warehouseId, weekAgo)
                .orElse(0.0);
    }

    private LocalDate getLastReceiptDate(Integer warehouseId) {
        return transactionRepository.findLastDateByType(
                Transaction.TransactionType.SUPPLIER_TO_WAREHOUSE,
                warehouseId
        ).orElse(null);
    }

    private LocalDate getLastDeliveryDate(Integer warehouseId){
        return transactionRepository.findLastDateByType(
                Transaction.TransactionType.WAREHOUSE_TO_CUSTOMER,
                warehouseId
        ).orElse(null);
    }

    private LocalDateTime getStartDate() {
        return LocalDateTime.now()
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }
}
