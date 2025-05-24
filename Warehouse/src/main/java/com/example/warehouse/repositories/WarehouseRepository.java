package com.example.warehouse.repositories;

import com.example.warehouse.domain.Warehouse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository extends CrudRepository<Warehouse, Integer>, JpaRepository<Warehouse, Integer> {

    @Query("""
            SELECT w.id, w.name, w.capacity, w.occupiedCapacity, a.street, a.streetNumber ,c.name,
                   (
                        SELECT COUNT(*)
                        FROM Employee e
                        WHERE e.warehouse.id  = w.id
                   ) AS employeesCount,
                   (
                        SELECT COUNT(*)
                        FROM ProductInventory p
                        WHERE p.warehouse.id = w.id
                   ) AS productsCount,
                   (
                       SELECT COUNT(*)
                       FROM Transaction t
                       WHERE t.fromWarehouse.id = w.id OR t.toWarehouse.id = w.id
                   ) AS transactionsCount
            FROM Warehouse w
            LEFT JOIN w.address a
            LEFT JOIN a.city c
            GROUP BY w.id
            
            """)
    List<Object[]> findAllWithDetails();

    @EntityGraph(attributePaths = {
            "address.city",
            "productInventories.product",
    })
    Optional<Warehouse> findWithDetailsById(Integer id);
}
