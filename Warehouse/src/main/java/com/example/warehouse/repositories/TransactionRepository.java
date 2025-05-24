package com.example.warehouse.repositories;

import com.example.warehouse.domain.Transaction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer>, JpaSpecificationExecutor<Transaction> {
    @Query("SELECT COUNT(t) FROM Transaction t WHERE " +
            "t.transactionType = :type AND " +
            "t.date BETWEEN :start AND :end AND " +
            "(:warehouseId IS NULL OR " +
            "(:type = 'SUPPLIER_TO_WAREHOUSE' AND t.toWarehouse.id = :warehouseId) OR " +
            "(:type = 'WAREHOUSE_TO_CUSTOMER' AND t.fromWarehouse.id = :warehouseId))")
    Integer countByTypeAndDateBetween(
            @Param("type") Transaction.TransactionType type,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("warehouseId") Integer warehouseId);


    @Query("SELECT MAX(FUNCTION('DATE', t.date)) FROM Transaction t WHERE " +
            "t.transactionType = :type AND " +
            "(:warehouseId IS NULL OR t.toWarehouse.id = :warehouseId OR t.fromWarehouse.id = :warehouseId)")
    Optional<LocalDate> findLastDateByType(
            @Param("type") Transaction.TransactionType type,
            @Param("warehouseId") Integer warehouseId);
}
