package com.example.warehouse.repositories;

import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.TransactionProduct;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionProductRepository extends JpaRepository<TransactionProduct, Integer> {

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

    @Query("""
                SELECT tp.product.id
                FROM TransactionProduct tp
                WHERE tp.transaction.date >= :fromDate
                AND (tp.transaction.fromWarehouse.id = :warehouseId OR tp.transaction.toWarehouse.id = :warehouseId)
                GROUP BY tp.product.id
                ORDER BY SUM(tp.quantity) DESC
            """)
    List<Integer> findTopNBestSellingProductsByWarehouseId(@Param("warehouseId") Integer warehouseId, @Param("fromDate") Date fromDate, Pageable pageable);

    @Query("""
               SELECT tp.product.id
               FROM TransactionProduct tp
               WHERE tp.transaction.date >= :fromDate
               GROUP BY tp.product.id
               ORDER BY SUM(tp.quantity) DESC
            """)
    List<Integer> findTopNBestSellingProducts(@Param("fromDate") Date fromDate, Pageable pageable);

    interface TopProductProjection {
        Product getProduct();

    }

    @EntityGraph(attributePaths = {
            "transaction.employee",
    })
    @Query("SELECT tp FROM TransactionProduct tp WHERE tp.product.id = :productId")
    List<TransactionProduct> findByProductId(@Param("productId") Integer productId);
}
