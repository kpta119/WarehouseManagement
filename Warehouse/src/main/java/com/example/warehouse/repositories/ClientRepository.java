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

    @Query(value = """
    SELECT c, COUNT(t)
    FROM Client c
    LEFT JOIN Transaction t ON t.client.id = c.id AND (:warehouseId IS NULL OR t.fromWarehouse.id = :warehouseId)
    WHERE (:regionName IS NULL OR c.address.city.country.region.name = :regionName)
    GROUP BY c
    HAVING (:minTransactions IS NULL OR 
            (SELECT COUNT(tAll) FROM Transaction tAll WHERE tAll.client.id = c.id) >= :minTransactions)
       AND (:maxTransactions IS NULL OR 
            (SELECT COUNT(tAll) FROM Transaction tAll WHERE tAll.client.id = c.id) <= :maxTransactions)
    """,
        countQuery = """
    SELECT COUNT(*) FROM Client c
    WHERE (:regionName IS NULL OR c.address.city.country.region.name = :regionName)
      AND (:minTransactions IS NULL OR 
           (SELECT COUNT(tAll) FROM Transaction tAll WHERE tAll.client.id = c.id) >= :minTransactions)
      AND (:maxTransactions IS NULL OR 
           (SELECT COUNT(tAll) FROM Transaction tAll WHERE tAll.client.id = c.id) <= :maxTransactions)
    """)
    Page<Object[]> findAllClientsWithTransactionCounts(String regionName, Integer minTransactions, Integer maxTransactions, Integer warehouseId, Pageable pageable);

    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.transactions WHERE c.id = :clientId")
    Optional<Client> findClientWithHistoryById(Integer clientId);
}
