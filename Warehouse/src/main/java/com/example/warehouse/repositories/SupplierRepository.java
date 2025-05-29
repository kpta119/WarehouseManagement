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

    @Query(value = """
    SELECT s, COUNT(t)
    FROM Supplier s
    LEFT JOIN Transaction t ON t.supplier.id = s.id AND (:warehouseId IS NULL OR t.fromWarehouse.id = :warehouseId)
    WHERE (:regionName IS NULL OR s.address.city.country.region.name = :regionName)
    GROUP BY s
    HAVING (:minTransactions IS NULL OR 
            (SELECT COUNT(tAll) FROM Transaction tAll WHERE tAll.supplier.id = s.id) >= :minTransactions)
       AND (:maxTransactions IS NULL OR 
            (SELECT COUNT(tAll) FROM Transaction tAll WHERE tAll.supplier.id = s.id) <= :maxTransactions)
    """,
            countQuery = """
    SELECT COUNT(*) FROM Supplier s
    WHERE (:regionName IS NULL OR s.address.city.country.region.name = :regionName)
      AND (:minTransactions IS NULL OR 
           (SELECT COUNT(tAll) FROM Transaction tAll WHERE tAll.supplier.id = s.id) >= :minTransactions)
      AND (:maxTransactions IS NULL OR 
           (SELECT COUNT(tAll) FROM Transaction tAll WHERE tAll.supplier.id = s.id) <= :maxTransactions)
    """)
    Page<Object[]> findAllSuppliersWithTransactionCounts(String regionName, Integer minTransactions, Integer maxTransactions, Integer warehouseId, Pageable pageable);

    @Query("SELECT s FROM Supplier s LEFT JOIN FETCH s.transactions WHERE s.id = :supplierId")
    Optional<Supplier> findSupplierWithHistoryById(Integer supplierId);

    @Query("SELECT s FROM Supplier s WHERE s.id = :id")
    Supplier findById(int id);
}
