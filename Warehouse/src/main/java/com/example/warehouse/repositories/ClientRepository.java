package com.example.warehouse.repositories;

import com.example.warehouse.domain.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {

    @Query("SELECT c, COUNT(t) FROM Client c LEFT JOIN Transaction t ON t.client.id = c.id GROUP BY c")
    List<Object[]> findAllClientsWithTransactionCounts();
}
