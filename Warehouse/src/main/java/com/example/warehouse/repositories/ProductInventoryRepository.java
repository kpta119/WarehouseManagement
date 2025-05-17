package com.example.warehouse.repositories;

import com.example.warehouse.domain.ProductInventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInventoryRepository extends CrudRepository<ProductInventory, Integer> {
}
