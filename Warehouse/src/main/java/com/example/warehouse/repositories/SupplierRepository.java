package com.example.warehouse.repositories;

import com.example.warehouse.domain.Supplier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Integer> {
}
