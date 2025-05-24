package com.example.warehouse.services;

import com.example.warehouse.domain.dto.dashboardDtos.SummaryDto;
import org.springframework.stereotype.Service;

@Service
public interface DashboardService {
    SummaryDto getSummary(Integer warehouseId);
}
