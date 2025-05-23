package com.example.warehouse.repositories;

import com.example.warehouse.domain.Transaction;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer>,
        JpaSpecificationExecutor<Transaction>, JpaRepository<Transaction, Integer> {

    @EntityGraph(attributePaths = {
            "products",
    })
    @Query("""
            SELECT t FROM Transaction t
            WHERE t.fromWarehouse.id = :warehouseId OR t.toWarehouse.id = :warehouseId
            """)
    List<Transaction> findAllByWarehouseId(Integer warehouseId);
}
