package com.example.warehouse.repositories;

import com.example.warehouse.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    @Query(value = "SELECT e, COUNT(t) FROM Employee e LEFT JOIN e.transactions t WHERE (:warehouseId IS NULL OR e.warehouse.id = :warehouseId) GROUP BY e.id",
    countQuery = "SELECT COUNT (DISTINCT e) FROM Employee e")
    Page<Object[]> findAllEmployeesWithTransactionCounts(Integer warehouseId, Pageable pageable);

    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.transactions WHERE e.id = :employeeId")
    Optional<Employee> findEmployeeWithHistoryById(Integer employeeId);
}
