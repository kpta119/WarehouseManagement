package com.example.warehouse.repositories;

import com.example.warehouse.domain.Client;
import com.example.warehouse.domain.dto.filtersDto.ClientSearchFilters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {

    @Query("""
    SELECT c, COUNT(t)
    FROM Client c
      LEFT JOIN Transaction t ON t.client.id = c.id AND (:#{#filters.warehouseId} IS NULL OR t.fromWarehouse.id = :#{#filters.warehouseId})
      WHERE (:#{#filters.regionId} IS NULL OR c.address.city.country.region.id = :#{#filters.regionId})
        AND (:#{#filters.name} IS NULL OR c.name LIKE CONCAT('%', :#{#filters.name}, '%'))
      GROUP BY c
      HAVING (:#{#filters.minTransactions} IS NULL OR COUNT(t) >= :#{#filters.minTransactions})
         AND (:#{#filters.maxTransactions} IS NULL OR COUNT(t) <= :#{#filters.maxTransactions})
    
    """)
    Page<Object[]> findAllClientsWithTransactionCounts(@Param("filters")ClientSearchFilters filters, Pageable pageable);

    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.transactions WHERE c.id = :clientId")
    Optional<Client> findClientWithHistoryById(Integer clientId);
}
