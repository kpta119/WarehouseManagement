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

    @Query(
            value = "SELECT e, COUNT(t) FROM Employee e " +
                    "LEFT JOIN e.transactions t " +
                    "JOIN e.address a " +
                    "JOIN a.city c " +
                    "JOIN c.country co " +
                    "JOIN co.region r " +
                    "WHERE (:warehouseId IS NULL OR e.warehouse.id = :warehouseId) " +
                    "AND (:partOfName IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :partOfName, '%')) OR LOWER(e.surname) LIKE LOWER(CONCAT('%', :partOfName, '%')))" +
                    "AND (:regionName IS NULL OR r.name = :regionName) " +
                    "GROUP BY e " +
                    "HAVING (:minTransactions IS NULL OR COUNT(t) >= :minTransactions) " +
                    "AND (:maxTransactions IS NULL OR COUNT(t) <= :maxTransactions)",
            countQuery = "SELECT COUNT(e) FROM Employee e " +
                    "JOIN e.address a " +
                    "JOIN a.city c " +
                    "JOIN c.country co " +
                    "JOIN co.region r " +
                    "WHERE (:warehouseId IS NULL OR e.warehouse.id = :warehouseId) " +
                    "AND (:partOfName IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :partOfName, '%')) OR LOWER(e.surname) LIKE LOWER(CONCAT('%', :partOfName, '%')))" +
                    "AND (:regionName IS NULL OR r.name = :regionName)" +
                    "AND (:minTransactions IS NULL OR (SELECT COUNT(t) FROM e.transactions t) >= :minTransactions) " +
                    "AND (:maxTransactions IS NULL OR (SELECT COUNT(t) FROM e.transactions t) <= :maxTransactions)")
    Page<Object[]> findAllEmployeesWithTransactionCounts(String partOfName, String regionName, Integer minTransactions, Integer maxTransactions, Integer warehouseId, Pageable pageable);

    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.transactions WHERE e.id = :employeeId")
    Optional<Employee> findEmployeeWithHistoryById(Integer employeeId);
}
