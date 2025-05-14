package com.example.warehouse.repositories;

import com.example.warehouse.domain.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Integer> {
}
