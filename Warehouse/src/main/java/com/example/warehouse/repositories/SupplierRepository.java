package com.example.warehouse.repositories;

import com.example.warehouse.domain.Supplier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Integer> {

    @Query("SELECT s, COUNT (t) from Supplier s LEFT JOIN Transaction t ON t.supplier.id = s.id group by s")
    List<Object[]> findAllSuppliersWithTransactionCounts();
}
