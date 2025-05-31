package com.example.warehouse.repositories;

import com.example.warehouse.domain.Supplier;
import com.example.warehouse.domain.dto.filtersDto.SupplierSearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Integer>, JpaRepository<Supplier, Integer> {

    @Query("""
    SELECT s, COUNT(t)
    FROM Supplier s
    LEFT JOIN Transaction t ON t.supplier.id = s.id AND (:#{#filter.warehouseId} IS NULL OR t.toWarehouse.id = :#{#filter.warehouseId})
    WHERE (:#{#filter.regionId} IS NULL OR s.address.city.country.region.id = :#{#filter.regionId})
    AND (:#{#filter.name} IS NULL OR s.name LIKE CONCAT('%', :#{#filter.name}, '%'))
    GROUP BY s
    HAVING (:#{#filter.minTransactions} IS NULL OR COUNT(t) >= :#{#filter.minTransactions})
    AND (:#{#filter.maxTransactions} IS NULL OR COUNT(t) <= :#{#filter.maxTransactions})
    """)
    Page<Object[]> findAllSuppliersWithTransactionCounts(@Param("filter") SupplierSearchFilter filter, Pageable pageable);

    @Query("SELECT s FROM Supplier s LEFT JOIN FETCH s.transactions WHERE s.id = :supplierId")
    Optional<Supplier> findSupplierWithHistoryById(Integer supplierId);

    @Query("SELECT s FROM Supplier s WHERE s.id = :id")
    Supplier findById(int id);
}
