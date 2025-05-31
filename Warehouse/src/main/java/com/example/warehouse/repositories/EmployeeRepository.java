package com.example.warehouse.repositories;

import com.example.warehouse.domain.Employee;
import com.example.warehouse.domain.dto.filtersDto.EmployeeSearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    @Query("""
            SELECT e, COUNT(t) FROM Employee e
            LEFT JOIN e.transactions t
            JOIN e.address a
            JOIN a.city c
            JOIN c.country co
            JOIN co.region r
            WHERE (:#{#filters.warehouseId} IS NULL OR e.warehouse.id = :#{#filters.warehouseId})
            AND (:#{#filters.partOfNameOrSurname} IS NULL OR
                LOWER(CONCAT(e.name, e.surname)) LIKE LOWER(CONCAT('%', :#{#filters.partOfNameOrSurname}, '%')))
            AND (:#{#filters.regionId} IS NULL OR r.id = :#{#filters.regionId})
            GROUP BY e
            HAVING (:#{#filters.minTransaction} IS NULL OR COUNT(t) >= :#{#filters.minTransaction})
            AND (:#{#filters.maxTransactions} IS NULL OR COUNT(t) <= :#{#filters.maxTransactions})
    """)
    Page<Object[]> findAllEmployeesWithTransactionCounts(@Param("filters") EmployeeSearchFilter filters, Pageable pageable);

    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.transactions WHERE e.id = :employeeId")
    Optional<Employee> findEmployeeWithHistoryById(Integer employeeId);
}