package com.example.warehouse.services;

import com.example.warehouse.domain.*;
import com.example.warehouse.dtos.InventoryOperationsDtos.ReceiveDeliveryDto;
import com.example.warehouse.dtos.InventoryOperationsDtos.SellToClientDto;
import com.example.warehouse.dtos.InventoryOperationsDtos.TransferBetweenDto;
import com.example.warehouse.dtos.transactionDtos.TransactionDataBaseDto;
import com.example.warehouse.mappers.InventoryOperationsMapper;
import com.example.warehouse.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryOperationsService {

    private final TransactionRepository transactionRepository;
    private final ObjectMapper objectMapper;
    private final SupplierRepository supplierRepository;
    private final WarehouseRepository warehouseRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final InventoryOperationsMapper inventoryOperationsMapper;

    public InventoryOperationsService(
            TransactionRepository transactionRepository,
            SupplierRepository supplierRepository,
            WarehouseRepository warehouseRepository,
            EmployeeRepository employeeRepository,
            ClientRepository clientRepository,
            InventoryOperationsMapper inventoryOperationsMapper
    ) {
        this.warehouseRepository = warehouseRepository;
        this.transactionRepository = transactionRepository;
        this.objectMapper = new ObjectMapper();
        this.supplierRepository = supplierRepository;
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
        this.inventoryOperationsMapper = inventoryOperationsMapper;

    }

    public TransactionDataBaseDto receiveDelivery(ReceiveDeliveryDto receiveTransferDto) throws Exception {
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

        Transaction lastTransaction = transactionRepository.findLastAddedTransactionByWarehouseId(receiveTransferDto.getWarehouseId(), PageRequest.of(0, 1))
                .stream().findFirst().orElseThrow(() -> new Exception("No transactions found for warehouse ID: " + receiveTransferDto.getWarehouseId()));
        return inventoryOperationsMapper.mapToDto(lastTransaction);
    }

    public List<Map<String, Integer>> transformItems(Map<String, Integer> items) {
        return items.entrySet().stream()
                .map(entry -> Map.of("ProductID", Integer.valueOf(entry.getKey()), "Quantity", entry.getValue()))
                .collect(Collectors.toList());
    }

    public TransactionDataBaseDto transferBetweenWarehouses(TransferBetweenDto transferDto) throws Exception {
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

        try {
            transactionRepository.exchangeBetweenWarehouses(
                    LocalDate.now(),
                    description,
                    transferDto.getEmployeeId(),
                    transferDto.getFromWarehouseId(),
                    transferDto.getToWarehouseId(),
                    productsJson
            );
        } catch (DataAccessException ex) {
            Throwable root = ex.getRootCause();
            if (root instanceof SQLException sqlEx && "45000".equals(sqlEx.getSQLState())) {
                throw new NonTransientDataAccessException(
                        "Transfer failed due to insufficient stock in the source warehouse: " + fromWarehouse.getName(), sqlEx) {};
            }
        }

        Transaction lastTransaction = transactionRepository.findLastAddedTransactionByWarehouseId(transferDto.getToWarehouseId(), PageRequest.of(0, 1))
                .stream().findFirst().orElseThrow(() -> new Exception("No transactions found for warehouse ID: " + transferDto.getToWarehouseId()));
        return inventoryOperationsMapper.mapToDto(lastTransaction);

    }

    public TransactionDataBaseDto sellToClient(SellToClientDto transferDto) throws Exception {
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

        try {
            transactionRepository.sellToClient(
                    LocalDate.now(),
                    description,
                    transferDto.getEmployeeId(),
                    transferDto.getWarehouseId(),
                    transferDto.getClientId(),
                    productsJson
            );
        } catch (DataAccessException ex) {
            Throwable root = ex.getRootCause();
            if (root instanceof SQLException sqlEx && "45000".equals(sqlEx.getSQLState())) {
                throw new NonTransientDataAccessException(
                        "Transfer failed due to insufficient stock in the source warehouse: " + warehouse.getName(), sqlEx) {};
            }
        }

        Transaction lastTransaction = transactionRepository.findLastAddedTransactionByWarehouseId(transferDto.getWarehouseId(), PageRequest.of(0, 1))
                .stream().findFirst().orElseThrow(() -> new Exception("No transactions found for warehouse ID: " + transferDto.getWarehouseId()));
        return inventoryOperationsMapper.mapToDto(lastTransaction);
    }
}
