package com.example.warehouse.repositories;

import com.example.warehouse.domain.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Integer>, JpaRepository<Supplier, Integer> {

    @Query(value = "SELECT s, COUNT(t) " +
            "FROM Supplier s " +
            "LEFT JOIN s.transactions t WITH (:warehouseId IS NULL OR t.toWarehouse.id = :warehouseId)" +
            "JOIN s.address a " +
            "JOIN a.city ci " +
            "JOIN ci.country co " +
            "JOIN co.region r " +
            "WHERE :regionName IS NULL OR r.name = :regionName" +
            " GROUP BY s " +
            "HAVING (:minTransactions IS NULL OR COUNT(t) >= :minTransactions) " +
            "   AND (:maxTransactions IS NULL OR COUNT(t) <= :maxTransactions)",
            countQuery = "SELECT COUNT(s) " +
                    "FROM Supplier s " +
                    "JOIN s.address a " +
                    "JOIN a.city ci " +
                    "JOIN ci.country co " +
                    "JOIN co.region r " +
                    "WHERE :regionName IS NULL OR r.name = :regionName " +
                    "  AND (:minTransactions IS NULL OR (SELECT COUNT(t) FROM s.transactions t) >= :minTransactions) " +
                    "  AND (:maxTransactions IS NULL OR (SELECT COUNT(t) FROM s.transactions t) <= :maxTransactions)")
    Page<Object[]> findAllSuppliersWithTransactionCounts(String regionName, Integer minTransactions, Integer maxTransactions, Integer warehouseId, Pageable pageable);

    @Query("SELECT s FROM Supplier s LEFT JOIN FETCH s.transactions WHERE s.id = :supplierId")
    Optional<Supplier> findSupplierWithHistoryById(Integer supplierId);

    @Query("SELECT s FROM Supplier s WHERE s.id = :id")
    Supplier findById(int id);
}
