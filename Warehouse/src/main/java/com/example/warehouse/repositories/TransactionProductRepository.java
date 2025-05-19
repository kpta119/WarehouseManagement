package com.example.warehouse.repositories;

import com.example.warehouse.domain.TransactionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionProductRepository extends CrudRepository<TransactionProduct, Integer>, JpaRepository<TransactionProduct, Integer> {
    @Query("""
                    SELECT COUNT(tp) FROM TransactionProduct tp
                    JOIN tp.transaction t
                    WHERE tp.product.id = :productId
                    AND (t.fromWarehouse.id = :warehouseId OR t.toWarehouse.id = :warehouseId)
            
            """)
    Integer countTransactionsByProductIdAndWarehouseId(@Param("id") Integer productId, @Param("warehouseId") Integer warehouseId);

    @Query("SELECT COUNT(tp) FROM TransactionProduct tp WHERE tp.product.id = :id")
    Integer countTransactionByProductId(@Param("id") Integer productId);
}
