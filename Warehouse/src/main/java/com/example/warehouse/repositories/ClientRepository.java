package com.example.warehouse.repositories;

import com.example.warehouse.domain.Client;
import com.example.warehouse.dtos.clientAndSupplierDtos.ClientSummaryDto;
import com.example.warehouse.dtos.filtersDto.ClientSearchFilters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Query("""
    SELECT new com.example.warehouse.dtos.clientAndSupplierDtos.ClientSummaryDto(
        c.id, c.name, c.email, c.phoneNumber, Concat(a.street, ' ', a.streetNumber, ' ' , ci.name, ', ', co.name), Count(t)
        )
    FROM Client c
      LEFT JOIN Transaction t ON t.client.id = c.id AND (:#{#filters.warehouseId} IS NULL OR t.fromWarehouse.id = :#{#filters.warehouseId})
      LEFT JOIN c.address a
      LEFT JOIN a.city ci
      LEFT JOIN ci.country co
      WHERE (:#{#filters.regionId} IS NULL OR c.address.city.country.region.id = :#{#filters.regionId})
        AND (:#{#filters.name} IS NULL OR c.name LIKE CONCAT('%', :#{#filters.name}, '%'))
      GROUP BY c
      HAVING (:#{#filters.minTransactions} IS NULL OR COUNT(t) >= :#{#filters.minTransactions})
         AND (:#{#filters.maxTransactions} IS NULL OR COUNT(t) <= :#{#filters.maxTransactions})
    
    """)
    Page<ClientSummaryDto> findAllClientsWithTransactionCounts(@Param("filters")ClientSearchFilters filters, Pageable pageable);

    @EntityGraph(attributePaths = {
        "address",
        "address.city",
        "address.city.country",
        "transactions",
        "transactions.fromWarehouse",
        "transactions.client",
        "transactions.employee"
    })
    @Query("SELECT c FROM Client c WHERE c.id = :clientId")
    Optional<Client> findClientWithHistoryById(Integer clientId);
}
