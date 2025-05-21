package com.example.warehouse.repositories;

import com.example.warehouse.domain.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    @Query("SELECT e, COUNT(t) FROM Employee e LEFT JOIN e.transactions t WHERE (:warehouseId IS NULL OR e.warehouse.id = :warehouseId) GROUP BY e.id")
    List<Object[]> findAllEmployeesWithTransactionCounts(Integer warehouseId);

    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.transactions WHERE e.id = :employeeId")
    Optional<Employee> findEmployeeWithHistoryById(Integer employeeId);
}
