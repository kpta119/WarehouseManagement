package com.example.warehouse.services;

import com.example.warehouse.domain.Address;
import com.example.warehouse.domain.Employee;
import com.example.warehouse.domain.Warehouse;
import com.example.warehouse.dtos.employeeDtos.CreateEmployeeDto;
import com.example.warehouse.dtos.employeeDtos.EmployeeDto;
import com.example.warehouse.dtos.employeeDtos.EmployeeSummaryDto;
import com.example.warehouse.dtos.employeeDtos.EmployeeWithHistoryDto;
import com.example.warehouse.dtos.filtersDto.EmployeeSearchFilter;
import com.example.warehouse.dtos.transactionDtos.TransactionWithProductsDto;
import com.example.warehouse.mappers.EmployeeMapper;
import com.example.warehouse.repositories.EmployeeRepository;
import com.example.warehouse.repositories.WarehouseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmployeeService {
    private final AddressService addressService;
    private final EmployeeRepository employeeRepository;
    private final WarehouseRepository warehouseRepository;
    private final EmployeeMapper employeeMapper;
    private final HistoryService historyService;

    public EmployeeService(
            AddressService addressService,
            EmployeeRepository employeeRepository,
            WarehouseRepository warehouseRepository,
            EmployeeMapper employeeMapper,
            HistoryService historyService
    ) {
        this.employeeRepository = employeeRepository;
        this.warehouseRepository = warehouseRepository;
        this.employeeMapper = employeeMapper;
        this.historyService = historyService;
        this.addressService = addressService;
    }

    public Page<EmployeeSummaryDto> getEmployeesWithTransactionCount(EmployeeSearchFilter filters, Pageable pageable) {
        return employeeRepository.findAllEmployeesWithTransactionCounts(filters, pageable);
    }

    public EmployeeDto createEmployee(CreateEmployeeDto employeeDto) {
        Address savedAddress = addressService.createAddress(employeeDto.getAddress());

        Integer warehouseId = employeeDto.getWarehouseId();
        Optional<Warehouse> existingWarehouse = warehouseRepository.findById(warehouseId);
        if (existingWarehouse.isEmpty()) {
            throw new NoSuchElementException("Warehouse with this id does not exist");
        }
        Employee employee = new Employee();
        employee.setEmail(employeeDto.getEmail());
        employee.setName(employeeDto.getName());
        employee.setSurname(employeeDto.getSurname());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setPosition(employeeDto.getPosition());
        employee.setAddress(savedAddress);
        employee.setWarehouse(existingWarehouse.get());
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.mapToDto(savedEmployee);
    }

    public EmployeeWithHistoryDto getEmployeeWithHistory(Integer employeeId) {
        Employee employee = employeeRepository.findEmployeeWithHistoryById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));
        List<TransactionWithProductsDto> transactions = historyService.getTransactionsHistory(employee.getTransactions());
        return employeeMapper.mapToDto(employee, transactions);
    }
}
