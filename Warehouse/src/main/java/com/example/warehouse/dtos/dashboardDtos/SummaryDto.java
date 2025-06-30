package com.example.warehouse.dtos.dashboardDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryDto {
    Integer productsCount;
    Integer categoriesCount;
    Integer monthlyReceipts;
    Integer monthlyDeliveries;
    Integer lowStockCount;
    String topProduct;
    Double inventoryValue;
    Double turnoverLastWeek;
    LocalDate lastReceiptDate;
    LocalDate lastDeliveryDate;
}
