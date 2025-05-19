package com.example.warehouse.repositories;

import com.example.warehouse.domain.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
}
