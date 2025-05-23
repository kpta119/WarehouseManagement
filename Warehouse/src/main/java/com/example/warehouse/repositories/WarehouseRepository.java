package com.example.warehouse.repositories;

import com.example.warehouse.domain.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends CrudRepository<Warehouse, Integer>, JpaRepository<Warehouse, Integer> {

    @Query("""
            SELECT w.id, w.name, w.capacity, w.occupiedCapacity, w.address.street, w.address.streetNumber ,w.address.city.name,
                   COUNT(DISTINCT e.id) AS employeesCount,
                   COUNT(DISTINCT p.id) AS productsCount,
                   COUNT(DISTINCT t.id) AS transactionsCount
            FROM Warehouse w LEFT JOIN Employee e on e.warehouse.id = w.id
            LEFT JOIN ProductInventory p on p.warehouse.id = w.id
            LEFT JOIN Transaction t on t.fromWarehouse.id = w.id OR t.toWarehouse.id = w.id
            GROUP BY w.id
            
            """)
    List<Object[]> findAllWithDetails();
}
