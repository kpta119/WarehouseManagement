package com.example.warehouse.repositories;

import com.example.warehouse.domain.Supplier;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.SupplierSummaryDto;
import com.example.warehouse.domain.dto.filtersDto.SupplierSearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    @Query("""
            SELECT new com.example.warehouse.domain.dto.clientAndSupplierDtos.SupplierSummaryDto(
                s.id, s.name, s.email, s.phoneNumber, Concat(a.street, ' ', a.streetNumber, ' ' , ci.name, ', ', co.name), Count(t)
                )
            FROM Supplier s
            LEFT JOIN Transaction t ON t.supplier.id = s.id AND (:#{#filter.warehouseId} IS NULL OR t.toWarehouse.id = :#{#filter.warehouseId})
            LEFT JOIN s.address a
            LEFT JOIN a.city ci
            LEFT JOIN ci.country co
            WHERE (:#{#filter.regionId} IS NULL OR s.address.city.country.region.id = :#{#filter.regionId})
            AND (:#{#filter.name} IS NULL OR s.name LIKE CONCAT('%', :#{#filter.name}, '%'))
            GROUP BY s
            HAVING (:#{#filter.minTransactions} IS NULL OR COUNT(t) >= :#{#filter.minTransactions})
            AND (:#{#filter.maxTransactions} IS NULL OR COUNT(t) <= :#{#filter.maxTransactions})
            """)
    Page<SupplierSummaryDto> findAllSuppliersWithTransactionCounts(@Param("filter") SupplierSearchFilter filter, Pageable pageable);

    @EntityGraph(attributePaths = {
            "address",
            "address.city",
            "address.city.country",
            "transactions",
            "transactions.supplier",
            "transactions.toWarehouse",
            "transactions.employee"
    })
    @Query("SELECT s FROM Supplier s WHERE s.id = :supplierId")
    Optional<Supplier> findSupplierWithHistoryById(Integer supplierId);
}
