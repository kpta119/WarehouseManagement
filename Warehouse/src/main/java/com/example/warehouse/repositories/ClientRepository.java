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


    @Query(value = "SELECT c, COUNT(t) " +
            "FROM Client c " +
            "LEFT JOIN c.transactions t WITH (:warehouseId IS NULL OR t.fromWarehouse.id = :warehouseId)" +
            "JOIN c.address a " +
            "JOIN a.city ci " +
            "JOIN ci.country co " +
            "JOIN co.region r " +
            "WHERE :regionName IS NULL OR r.name = :regionName" +
            " GROUP BY c " +
            "HAVING (:minTransactions IS NULL OR COUNT(t) >= :minTransactions) " +
            "   AND (:maxTransactions IS NULL OR COUNT(t) <= :maxTransactions)",
            countQuery = "SELECT COUNT(c) " +
                    "FROM Client c " +
                    "JOIN c.address a " +
                    "JOIN a.city ci " +
                    "JOIN ci.country co " +
                    "JOIN co.region r " +
                    "WHERE :regionName IS NULL OR r.name = :regionName " +
                    "  AND (:minTransactions IS NULL OR (SELECT COUNT(t) FROM c.transactions t) >= :minTransactions) " +
                    "  AND (:maxTransactions IS NULL OR (SELECT COUNT(t) FROM c.transactions t) <= :maxTransactions)")
    Page<Object[]> findAllClientsWithTransactionCounts(String regionName, Integer minTransactions, Integer maxTransactions, Integer warehouseId, Pageable pageable);

    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.transactions WHERE c.id = :clientId")
    Optional<Client> findClientWithHistoryById(Integer clientId);
}
