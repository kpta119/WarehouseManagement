package com.example.warehouse.repositories;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.domain.dto.filtersDto.TransactionsSearchFilters;
import com.example.warehouse.domain.dto.transactionDtos.TransactionSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
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

    @EntityGraph(attributePaths = {
            "products",
            "employee",
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


    @Procedure(procedureName = "exchange_between_warehouses")
    void exchangeBetweenWarehouses(@Param("p_Date") LocalDate date,
                                   @Param("p_Description") String description,
                                   @Param("p_EmployeeID") Integer employeeId,
                                   @Param("p_FromWarehouseID") Integer fromWarehouseId,
                                   @Param("p_ToWarehouseID") Integer toWarehouseId,
                                   @Param("p_ProductsJSON") String productsJson);

    @Procedure(procedureName = "sell_to_client")
    void sellToClient(@Param("p_Date") LocalDate date,
                      @Param("p_Description") String description,
                      @Param("p_EmployeeID") Integer employeeId,
                      @Param("p_FromWarehouseID") Integer warehouseId,
                      @Param("p_ClientID") Integer clientId,
                      @Param("p_ProductsJSON") String productsJson);

    @Query("""
            SELECT t FROM Transaction t
            WHERE t.toWarehouse.id = :warehouseId OR t.fromWarehouse.id = :warehouseId
            ORDER BY t.id DESC
            """)
    List<Transaction> findLastAddedTransactionByWarehouseId(Integer warehouseId, Pageable pageable);


    @Query("""
            SELECT new com.example.warehouse.domain.dto.transactionDtos.TransactionSummaryDto(
                 t.id, t.date, t.description, t.transactionType, concat(t.employee.name, " ", t.employee.surname), fw.name, tw.name,
                 c.name, s.name, SUM(tp.transactionPrice * tp.quantity), SUM(p.unitSize * tp.quantity)
                )
            FROM Transaction t
            LEFT JOIN t.client   c
            LEFT JOIN t.supplier s
            Left Join t.fromWarehouse fw
            LEFT JOIN t.toWarehouse tw
            JOIN t.products tp
            JOIN tp.product p
            WHERE (:#{#filters.fromDate} IS NULL OR t.date >= :#{#filters.fromDate})
              AND (:#{#filters.toDate} IS NULL OR t.date <= :#{#filters.toDate})
              AND (:#{#filters.type} IS NULL OR t.transactionType = :#{#filters.type})
              AND (:#{#filters.employeeId} IS NULL OR t.employee.id = :#{#filters.employeeId})
                AND (:#{#filters.warehouseId} IS NULL OR (t.fromWarehouse.id = :#{#filters.warehouseId} OR t.toWarehouse.id = :#{#filters.warehouseId}))
            GROUP BY t.id
            HAVING (:#{#filters.minTotalPrice} IS NULL OR SUM(tp.transactionPrice * tp.quantity) >= :#{#filters.minTotalPrice})
               AND (:#{#filters.maxTotalPrice} IS NULL OR SUM(tp.transactionPrice * tp.quantity) <= :#{#filters.maxTotalPrice})
               AND (:#{#filters.minTotalSize} IS NULL OR SUM(p.unitSize * tp.quantity) >= :#{#filters.minTotalSize})
               AND (:#{#filters.maxTotalSize} IS NULL OR SUM(p.unitSize * tp.quantity) <= :#{#filters.maxTotalSize})
            ORDER BY t.date DESC
    """)
    Page<TransactionSummaryDto> findAllWithFilters(@Param("filters") TransactionsSearchFilters filters, @Param("fromDate") Date fromDate, Pageable pageable);

    @EntityGraph(attributePaths = {
            "fromWarehouse",
            "toWarehouse",
            "employee",
            "client",
            "employee",
            "products.product",
    })
    @Query("SELECT t from Transaction t where t.id = :transactionId")
    Optional<Transaction> findTransactionHistoryById(Integer transactionId);
}
