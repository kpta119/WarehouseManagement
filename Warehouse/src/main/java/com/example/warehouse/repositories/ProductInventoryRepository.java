package com.example.warehouse.repositories;

import com.example.warehouse.domain.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Integer> {
    @Query("SELECT Count(pi) FROM ProductInventory pi " +
            "WHERE (:warehouseId IS NULL OR pi.warehouse.id = :warehouseId)")
    Integer countByWarehouse(Integer warehouseId);


    @Query("SELECT COUNT(DISTINCT pi.product.category) FROM ProductInventory pi WHERE " +
            "(:warehouseId IS NULL OR pi.warehouse.id = :warehouseId)")
    Integer countDistinctCategoriesByWarehouse(Integer warehouseId);

    @Query("SELECT COUNT(pi) FROM ProductInventory pi WHERE " +
            "pi.quantity < 5 AND " +
            "(:warehouseId IS NULL OR pi.warehouse.id = :warehouseId)")
    Integer countLowStockByWarehouse(Integer warehouseId);

    @Query("SELECT SUM(pi.quantity * pi.product.unitPrice) " +
            "FROM ProductInventory pi " +
            "WHERE (:warehouseId IS NULL OR pi.warehouse.id = :warehouseId)")
    Optional<Double> calculateInventoryValue(Integer warehouseId);

    @Query("SELECT pi.product.id FROM ProductInventory pi WHERE pi.quantity < :lowStockThreshold AND pi.warehouse.id = :warehouseId")
    List<Integer> findLowStockProductsByWarehouseId(@Param("warehouseId") Integer warehouseId, @Param("lowStockThreshold") int lowStockThreshold);

    @Query("""
            
                    SELECT pi.product.id
                    FROM ProductInventory pi
                    GROUP BY pi.product.id
                    HAVING SUM(pi.quantity) < :lowStockThreshold
            
            """)
    List<Integer> findLowStockProducts(@Param("lowStockThreshold") int lowStockThreshold);
}
