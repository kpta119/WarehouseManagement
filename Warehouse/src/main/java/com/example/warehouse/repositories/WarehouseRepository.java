package com.example.warehouse.repositories;

import com.example.warehouse.domain.Warehouse;
import com.example.warehouse.dtos.filtersDto.WarehousesSearchFilters;
import com.example.warehouse.dtos.warehouseDto.WarehouseGetAllResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    @Query("""
            SELECT new com.example.warehouse.dtos.warehouseDto.WarehouseGetAllResponseDto(
                        w.id, w.name, w.capacity, w.occupiedCapacity, Concat(a.street, ' ', a.streetNumber, ' ' , c.name, ', ', co.name),
                        Count(distinct e.id), COALESCE(Sum(distinct pi.quantity), 0L), Count(distinct t.id)
                        )
            FROM Warehouse w
            LEFT JOIN w.productInventories pi
            LEFT JOIN Transaction t ON t.fromWarehouse.id = w.id OR t.toWarehouse.id = w.id
            LEFT JOIN w.employees e
            LEFT JOIN w.address a
            LEFT JOIN a.city c
            LEFT JOIN c.country co
            LEFT JOIN co.region r
            WHERE (:#{#filter.minCapacity} IS NULL OR w.capacity >= :#{#filter.minCapacity})
            AND (:#{#filter.name} IS NULL OR w.name LIKE CONCAT('%', :#{#filter.name}, '%'))
            AND (:#{#filter.maxCapacity} IS NULL OR w.capacity <= :#{#filter.maxCapacity})
            AND (:#{#filter.minOccupied} IS NULL OR w.occupiedCapacity >= :#{#filter.minOccupied})
            AND (:#{#filter.maxOccupied} IS NULL OR w.occupiedCapacity <= :#{#filter.maxOccupied})
            AND (:#{#filter.regionId} IS NULL OR r.id = :#{#filter.regionId})
            GROUP BY w.id
            HAVING (:#{#filter.minEmployees} IS NULL OR Count(distinct e.id) >= :#{#filter.minEmployees})
            AND (:#{#filter.maxEmployees} IS NULL OR Count(distinct e.id) <= :#{#filter.maxEmployees})
            AND (:#{#filter.minProducts} IS NULL OR COALESCE(Sum(distinct pi.quantity), 0) >= :#{#filter.minProducts})
            AND (:#{#filter.maxProducts} IS NULL OR COALESCE(Sum(distinct pi.quantity), 0) <= :#{#filter.maxProducts})
            AND (:#{#filter.minTransactions} IS NULL OR Count(distinct t.id) >= :#{#filter.minTransactions})
            AND (:#{#filter.maxTransactions} IS NULL OR Count(distinct t.id) <= :#{#filter.maxTransactions})
            """)
    Page<WarehouseGetAllResponseDto> findAllWithDetails(@Param("filter") WarehousesSearchFilters filter, Pageable pageable);

    @EntityGraph(attributePaths = {
            "address.city",
            "productInventories.product",
    })
    Optional<Warehouse> findWithDetailsById(Integer id);
}
