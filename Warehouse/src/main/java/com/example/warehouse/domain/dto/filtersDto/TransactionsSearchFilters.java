package com.example.warehouse.domain.dto.filtersDto;

import com.example.warehouse.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsSearchFilters {
    private Double minTotalPrice;
    private Double maxTotalPrice;
    private Double minTotalSize;
    private Double maxTotalSize;
    private @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate;
    private @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate;
    private Transaction.TransactionType type;
    private Integer employeeId;
    private Integer warehouseId;
}
