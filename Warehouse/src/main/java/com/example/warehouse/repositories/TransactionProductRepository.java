package com.example.warehouse.repositories;

import com.example.warehouse.domain.TransactionProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionProductRepository extends CrudRepository<TransactionProduct, Integer> {
}
