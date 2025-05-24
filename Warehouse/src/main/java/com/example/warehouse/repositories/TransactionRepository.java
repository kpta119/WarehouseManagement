package com.example.warehouse.repositories;

import com.example.warehouse.domain.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    @Procedure(procedureName = "receive_delivery")
    void receiveDelivery(@Param("p_Date") LocalDate date,
                         @Param("p_Description") String description,
                         @Param("p_EmployeeID") Integer employeeId,
                         @Param("p_SupplierID") Integer supplierId,
                         @Param("p_ToWarehouseID") Integer warehouseId,
                         @Param("p_ProductsJSON") String productsJson);


    @Query("""
            SELECT t FROM Transaction t
            WHERE t.toWarehouse.id = :warehouseId OR t.fromWarehouse.id = :warehouseId
            ORDER BY t.id DESC
            """)
    List<Transaction> findLastAddedTransactionByWarehouseId(Integer warehouseId, Pageable pageable);
}
