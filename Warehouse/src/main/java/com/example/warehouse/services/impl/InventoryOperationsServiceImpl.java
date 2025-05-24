package com.example.warehouse.services.impl;

import com.example.warehouse.domain.*;
import com.example.warehouse.domain.dto.InventoryOperationsDtos.ReceiveDeliveryDto;
import com.example.warehouse.domain.dto.InventoryOperationsDtos.SellToClientDto;
import com.example.warehouse.domain.dto.InventoryOperationsDtos.TransferBetweenDto;
import com.example.warehouse.repositories.*;
import com.example.warehouse.services.InventoryOperationsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryOperationsServiceImpl implements InventoryOperationsService {

    private final TransactionRepository transactionRepository;
    private final ObjectMapper objectMapper;
    private final SupplierRepository supplierRepository;
    private final WarehouseRepository warehouseRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    public InventoryOperationsServiceImpl(TransactionRepository transactionRepository,
                                          SupplierRepository supplierRepository,
                                          WarehouseRepository warehouseRepository,
                                          EmployeeRepository employeeRepository,
                                          ClientRepository clientRepository) {
        this.warehouseRepository = warehouseRepository;
        this.transactionRepository = transactionRepository;
        this.objectMapper = new ObjectMapper();
        this.supplierRepository = supplierRepository;
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Transaction receiveDelivery(ReceiveDeliveryDto receiveTransferDto) throws Exception {
        List<Map<String, Integer>> items = this.transformItems(receiveTransferDto.getItems());
        String productsJson = objectMapper.writeValueAsString(items);
        Supplier supplier = supplierRepository.findById(receiveTransferDto.getSupplierId())
                .orElseThrow(() -> new Exception("Supplier not found with ID: " + receiveTransferDto.getSupplierId()));
        Employee employee = employeeRepository.findById(receiveTransferDto.getEmployeeId())
                .orElseThrow(() -> new Exception("Employee not found with ID: " + receiveTransferDto.getEmployeeId()));
        Warehouse warehouse = warehouseRepository.findById(receiveTransferDto.getWarehouseId())
                .orElseThrow(() -> new Exception("Warehouse not found with ID: " + receiveTransferDto.getWarehouseId()));
        String description = "Supplier: " + supplier.getName() + " supplied warehouse: " + warehouse.getName() +
                " by: " + employee.getName() + " " + employee.getSurname();
        transactionRepository.receiveDelivery(
                LocalDate.now(),
                description,
                receiveTransferDto.getEmployeeId(),
                receiveTransferDto.getSupplierId(),
                receiveTransferDto.getWarehouseId(),
                productsJson
                );

        return transactionRepository.findLastAddedTransactionByWarehouseId(receiveTransferDto.getWarehouseId(), PageRequest.of(0, 1))
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<Map<String, Integer>> transformItems(Map<String, Integer> items) {
        return items.entrySet().stream()
                .map(entry -> Map.of("ProductID", Integer.valueOf(entry.getKey()), "Quantity", entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public Transaction transferBetweenWarehouses(TransferBetweenDto transferDto) throws Exception {
        List<Map<String, Integer>> items = this.transformItems(transferDto.getItems());
        String productsJson = objectMapper.writeValueAsString(items);
        Employee employee = employeeRepository.findById(transferDto.getEmployeeId())
                .orElseThrow(() -> new Exception("Employee not found with ID: " + transferDto.getEmployeeId()));
        Warehouse fromWarehouse = warehouseRepository.findById(transferDto.getFromWarehouseId())
                .orElseThrow(() -> new Exception("From Warehouse not found with ID: " + transferDto.getFromWarehouseId()));
        Warehouse toWarehouse = warehouseRepository.findById(transferDto.getToWarehouseId())
                .orElseThrow(() -> new Exception("To Warehouse not found with ID: " + transferDto.getToWarehouseId()));
        String description = "Warehouse " + fromWarehouse.getName() + " supplied warehouse: " +
                toWarehouse.getName() + " by: " + employee.getName() + " " + employee.getSurname();

        transactionRepository.exchangeBetweenWarehouses(
                LocalDate.now(),
                description,
                transferDto.getEmployeeId(),
                transferDto.getFromWarehouseId(),
                transferDto.getToWarehouseId(),
                productsJson
        );

        return transactionRepository.findLastAddedTransactionByWarehouseId(transferDto.getToWarehouseId(), PageRequest.of(0, 1))
                .stream().findFirst().orElse(null);

    }

    @Override
    public Transaction sellToClient(SellToClientDto transferDto) throws Exception {
        List<Map<String, Integer>> items = this.transformItems(transferDto.getItems());
        String productsJson = objectMapper.writeValueAsString(items);
        Employee employee = employeeRepository.findById(transferDto.getEmployeeId())
                .orElseThrow(() -> new Exception("Employee not found with ID: " + transferDto.getEmployeeId()));
        Warehouse warehouse = warehouseRepository.findById(transferDto.getWarehouseId())
                .orElseThrow(() -> new Exception("Warehouse not found with ID: " + transferDto.getWarehouseId()));
        Client client = clientRepository.findById(transferDto.getClientId())
                .orElseThrow(() -> new Exception("Client not found with ID: " + transferDto.getClientId()));
        String description = "Warehouse " + warehouse.getName() + " sold items to: " + client.getName() +
                " by: " + employee.getName() + " " + employee.getSurname();

        transactionRepository.sellToClient(
                LocalDate.now(),
                description,
                transferDto.getEmployeeId(),
                transferDto.getWarehouseId(),
                transferDto.getClientId(),
                productsJson
        );

        return transactionRepository.findLastAddedTransactionByWarehouseId(transferDto.getWarehouseId(), PageRequest.of(0, 1))
                .stream().findFirst().orElse(null);
    }
}
