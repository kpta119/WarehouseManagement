package com.example.warehouse.repositories;

import com.example.warehouse.domain.Transaction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer>, JpaSpecificationExecutor<Transaction> {
}
