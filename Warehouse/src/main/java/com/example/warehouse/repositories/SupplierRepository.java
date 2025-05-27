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

    @Query(value = "SELECT s, COUNT (t) from Supplier s LEFT JOIN Transaction t ON t.supplier.id = s.id group by s",
        countQuery = "SELECT COUNT (DISTINCT s) FROM Supplier s")
    Page<Object[]> findAllSuppliersWithTransactionCounts(Pageable pageable);

    @Query("SELECT s FROM Supplier s LEFT JOIN FETCH s.transactions WHERE s.id = :supplierId")
    Optional<Supplier> findSupplierWithHistoryById(Integer supplierId);

    @Query("SELECT s FROM Supplier s WHERE s.id = :id")
    Supplier findById(int id);
}
