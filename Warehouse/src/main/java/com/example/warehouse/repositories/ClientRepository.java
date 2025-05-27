package com.example.warehouse.repositories;

import com.example.warehouse.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {

    @Query(value = "SELECT c, COUNT(t) FROM Client c LEFT JOIN Transaction t ON t.client.id = c.id GROUP BY c",
        countQuery = "SELECT COUNT (DISTINCT c) FROM Client c")
    Page<Object[]> findAllClientsWithTransactionCounts(Pageable pageable);

    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.transactions WHERE c.id = :clientId")
    Optional<Client> findClientWithHistoryById(Integer clientId);
}
