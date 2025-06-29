package com.example.warehouse.services;

import com.example.warehouse.domain.dto.InventoryOperationsDtos.ReceiveDeliveryDto;
import com.example.warehouse.domain.dto.InventoryOperationsDtos.SellToClientDto;
import com.example.warehouse.domain.dto.InventoryOperationsDtos.TransferBetweenDto;
import com.example.warehouse.domain.dto.transactionDtos.TransactionDataBaseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface InventoryOperationsService {

    TransactionDataBaseDto receiveDelivery(ReceiveDeliveryDto receiveTransferDto) throws Exception;

    List<Map<String, Integer>> transformItems(Map<String, Integer> items);

    TransactionDataBaseDto transferBetweenWarehouses(TransferBetweenDto transferDto) throws Exception;

    TransactionDataBaseDto sellToClient(SellToClientDto transferDto) throws Exception;
}
