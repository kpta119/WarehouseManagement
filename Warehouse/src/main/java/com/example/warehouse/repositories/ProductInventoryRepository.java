package com.example.warehouse.repositories;

import com.example.warehouse.domain.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInventoryRepository extends CrudRepository<ProductInventory, Integer>, JpaRepository<ProductInventory, Integer> {
    @Query("SELECT SUM(pi.quantity) FROM ProductInventory pi WHERE pi.product.id = :productId AND pi.warehouse.id = :warehouseId")
    Integer getInventoryCountByProductIdAndWarehouseId(@Param("productId") Integer productId, @Param("warehouseId") Integer warehouseId);

    @Query("SELECT SUM(pi.quantity) FROM ProductInventory pi WHERE pi.product.id = :productId")
    Integer getInventoryCountByProductId(@Param("productId") Integer productId);
}
