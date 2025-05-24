package com.example.warehouse.repositories;

import com.example.warehouse.domain.ProductInventory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductInventoryRepository extends CrudRepository<ProductInventory, Integer> {
    @Query("SELECT COALESCE(SUM(pi.quantity), 0) FROM ProductInventory pi " +
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
}
