package com.example.warehouse.mappers;

import com.example.warehouse.domain.Employee;
import com.example.warehouse.domain.Transaction;
import com.example.warehouse.domain.Warehouse;
import com.example.warehouse.domain.dto.dateDtos.OccupancyDto;
import com.example.warehouse.domain.dto.employeeDtos.EmployeeDto;
import com.example.warehouse.domain.dto.transactionDtos.WarehouseTransactionInfoDto;
import com.example.warehouse.domain.dto.warehouseDto.ProductInWarehouseDto;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseDataBaseDto;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseDetailsDto;
import com.example.warehouse.domain.dto.warehouseDto.WarehouseGetAllEndpointDto;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;

@Component
public class WarehousesMapper {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public WarehouseGetAllEndpointDto mapToDto(Object[] warehouse) {
        WarehouseGetAllEndpointDto dto = new WarehouseGetAllEndpointDto();
        dto.setWarehouseId(((Number) warehouse[0]).intValue());
        dto.setName((String) warehouse[1]);
        dto.setCapacity((Double) warehouse[2]);
        dto.setOccupiedCapacity((double) warehouse[3]);
        dto.setAddress(
                warehouse[4] + " " + warehouse[5] + ", " + warehouse[6]
        );
        dto.setEmployeesCount(((Number) warehouse[7]).intValue());
        dto.setProductsCount(((Number) warehouse[8]).intValue());
        dto.setTransactionsCount(((Number) warehouse[9]).intValue());
        return dto;
    }

    public WarehouseDetailsDto mapToDto(Warehouse warehouse, List<Transaction> transactions, List<Employee> employees) {
        WarehouseDetailsDto dto = new WarehouseDetailsDto();
        dto.setWarehouseId(warehouse.getId());
        dto.setName(warehouse.getName());
        dto.setCapacity(warehouse.getCapacity());
        dto.setOccupiedCapacity(warehouse.getOccupiedCapacity());
        dto.setAddress(
                warehouse.getAddress().getStreet() + " " + warehouse.getAddress().getStreetNumber() + ", " + warehouse.getAddress().getCity().getName()
        );
        dto.setEmployees(employees.stream().map(employee -> {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setEmployeeId(employee.getId());
            employeeDto.setName(employee.getName());
            employeeDto.setSurname(employee.getSurname());
            employeeDto.setEmail(employee.getEmail());
            employeeDto.setPhoneNumber(employee.getPhoneNumber());
            employeeDto.setPosition(employee.getPosition());

            return employeeDto;
        }).toList());
        dto.setProducts(warehouse.getProductInventories().stream().map(productInventory -> {
            ProductInWarehouseDto productDto = new ProductInWarehouseDto();
            productDto.setProductId(productInventory.getProduct().getId());
            productDto.setName(productInventory.getProduct().getName());
            productDto.setQuantity(productInventory.getQuantity());
            productDto.setUnitPrice(productInventory.getProduct().getUnitPrice());

            return productDto;
        }).toList());
        dto.setTransactions(transactions.stream().map(transaction -> {
            WarehouseTransactionInfoDto transactionDto = new WarehouseTransactionInfoDto();
            transactionDto.setTransactionId(transaction.getId());
            transactionDto.setType(transaction.getTransactionType().name());
            transactionDto.setDate(dateFormat.format(transaction.getDate()));
            transactionDto.setDescription(transaction.getDescription());
            transactionDto.setTotalPrice(transaction.getTotalPrice());
            return transactionDto;
        }).toList());
        transactions.sort(Comparator.comparing(Transaction::getDate));
        dto.setOccupancyHistory(transactions.stream().map(transaction -> {
            OccupancyDto occupancyDto = new OccupancyDto();
            occupancyDto.setDate(dateFormat.format(transaction.getDate()));
            if (transaction.getFromWarehouse() != null) {
                occupancyDto.setOccupiedCapacity(transaction.getSourceWarehouseCapacityAfterTransaction());
            } else {
                occupancyDto.setOccupiedCapacity(transaction.getTargetWarehouseCapacityAfterTransaction());
            }
            return occupancyDto;
        }).toList());
        return dto;
    }

    public WarehouseDataBaseDto mapToDto(Warehouse warehouse) {
        WarehouseDataBaseDto dto = new WarehouseDataBaseDto();
        dto.setWarehouseId(warehouse.getId());
        dto.setName(warehouse.getName());
        dto.setCapacity(warehouse.getCapacity());
        dto.setOccupiedCapacity(warehouse.getOccupiedCapacity());
        dto.setAddressId(warehouse.getAddress().getId());
        return dto;
    }
}
