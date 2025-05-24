package com.example.warehouse.services;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.domain.dto.InventoryOperationsDtos.ReceiveDeliveryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface InventoryOperationsService {

    Transaction receiveDelivery(ReceiveDeliveryDto receiveTransferDto) throws Exception;

    List<Map<String, Integer>> transformItems(Map<String, Integer> items);
}
