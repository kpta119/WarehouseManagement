package com.example.warehouse.repositories;

import com.example.warehouse.domain.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInventoryRepository extends CrudRepository<ProductInventory, Integer>, JpaRepository<ProductInventory, Integer> {
    @Query("SELECT SUM(pi.quantity) FROM ProductInventory pi WHERE pi.product.id = :productId AND pi.warehouse.id = :warehouseId")
    Integer sumInventoryQuantityByProductIdAndWarehouseId(@Param("productId") Integer productId, @Param("warehouseId") Integer warehouseId);

    @Query("SELECT SUM(pi.quantity) FROM ProductInventory pi WHERE pi.product.id = :productId")
    Integer sumInventoryQuantityByProductId(@Param("productId") Integer productId);

    List<ProductInventory> findByProductId(Integer productId);

    @Query("SELECT pi FROM ProductInventory pi WHERE pi.quantity < :lowStockThreshold AND pi.warehouse.id = :warehouseId")
    List<ProductInventory> findLowStockProductsByWarehouseId(@Param("warehouseId") Integer warehouseId, @Param("lowStockThreshold") int lowStockThreshold);

    @Query("""
            
                    SELECT pi.product.id, SUM(pi.quantity)
                    FROM ProductInventory pi
                    GROUP BY pi.product.id
                    HAVING SUM(pi.quantity) < :lowStockThreshold
            
            """)
    List<Object[]> findLowStockProducts(@Param("lowStockThreshold") int lowStockThreshold);
}
