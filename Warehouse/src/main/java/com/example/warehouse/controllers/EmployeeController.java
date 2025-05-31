package com.example.warehouse.controllers;

import com.example.warehouse.domain.Employee;
import com.example.warehouse.domain.dto.employeeDtos.EmployeeDto;
import com.example.warehouse.domain.dto.employeeDtos.EmployeeSummaryDto;
import com.example.warehouse.domain.dto.employeeDtos.EmployeeWithHistoryDto;
import com.example.warehouse.domain.dto.filtersDto.EmployeeSearchFilter;
import com.example.warehouse.mappers.EmployeeMapper;
import com.example.warehouse.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping()
    public ResponseEntity<?> getAllEmployees(
            @ModelAttribute EmployeeSearchFilter filters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "false") boolean all
    ) {
        try {
            Pageable pageable;
            if (all) {
                pageable = Pageable.unpaged();
            } else {
                pageable = PageRequest.of(page, size);
            }
            Page<EmployeeSummaryDto> response = employeeService.getEmployeesWithTransactionCount(filters, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getEmployeeWithHistory(@PathVariable("employeeId") Integer employeeId) {
        try {
            EmployeeWithHistoryDto employeeWithHistory = employeeService.getEmployeeWithHistory(employeeId);
            return ResponseEntity.status(HttpStatus.OK).body(employeeWithHistory);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDto request) {
        try {
            Employee savedEmployee = employeeService.createEmployee(request);
            EmployeeDto responseDto = employeeMapper.mapToDto(savedEmployee);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }
}
