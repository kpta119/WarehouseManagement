package com.example.warehouse.repositories;

import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.TransactionProduct;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionProductRepository extends JpaRepository<TransactionProduct, Integer> {

    interface TopProductProjection {
        Product getProduct();
        Long getTotalQuantity();
    }

    @Query("SELECT tp.product AS product, SUM(tp.quantity) AS totalQuantity " +
            "FROM TransactionProduct tp " +
            "JOIN tp.transaction t " +
            "WHERE t.transactionType = 'WAREHOUSE_TO_CUSTOMER' " +
            "AND (:warehouseId IS NULL OR t.fromWarehouse.id = :warehouseId) " +
            "GROUP BY tp.product " +
            "ORDER BY totalQuantity DESC")
    List<TopProductProjection> findTopProducts(@Param("warehouseId") Integer warehouseId, Pageable pageable);

    @Query("SELECT SUM(tp.quantity * tp.transactionPrice) " +
            "FROM TransactionProduct tp " +
            "JOIN tp.transaction t " +
            "WHERE t.date >= :weekAgo AND " +
            "(:warehouseId IS NULL OR t.fromWarehouse.id = :warehouseId OR t.toWarehouse.id = :warehouseId)")
    Optional<Double> calculateTurnoverLastWeek(Integer warehouseId, LocalDateTime weekAgo);

    default Optional<TopProductProjection> findTopProduct(Integer warehouseId) {
        return findTopProducts(warehouseId, PageRequest.of(0, 1)).stream().findFirst();
    }


}
