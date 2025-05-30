package com.example.warehouse.repositories;

import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.dto.filtersDto.ProductSearchFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>, JpaRepository<Product, Integer> {
    @Query("""
                SELECT p.id, p.name, p.description, p.unitPrice, p.unitSize, COALESCE(SUM(distinct pi.quantity), 0) as totalInventoryCount, Count(distinct tp.id),
                       c.name AS categoryName
                FROM Product p
                LEFT JOIN p.productInventories pi WITH (:#{#filter.warehouseId} IS NULL OR pi.warehouse.id = :#{#filter.warehouseId})
                LEFT JOIN p.productTransactions tp WITH (:#{#filter.warehouseId} IS NULL OR tp.transaction.fromWarehouse.id = :#{#filter.warehouseId} OR tp.transaction.toWarehouse.id = :#{#filter.warehouseId})
                JOIN p.category c
                WHERE (:#{#filter.warehouseId} IS NULL OR pi.warehouse.id = :#{#filter.warehouseId})
                AND (:#{#filter.name} IS NULL OR p.name LIKE CONCAT('%', :#{#filter.name}, '%'))
                AND (:#{#filter.categoryId} IS NULL OR c.id = :#{#filter.categoryId})
                AND (:#{#filter.minPrice} IS NULL OR p.unitPrice >= :#{#filter.minPrice})
                AND (:#{#filter.maxPrice} IS NULL OR p.unitPrice <= :#{#filter.maxPrice})
                AND (:#{#filter.minSize} IS NULL OR p.unitSize >= :#{#filter.minSize})
                AND (:#{#filter.maxSize} IS NULL OR p.unitSize <= :#{#filter.maxSize})
                GROUP BY p.id
                HAVING (:#{#filter.minInventory} IS NULL OR COALESCE(SUM(distinct pi.quantity), 0) >= :#{#filter.minInventory})
                AND (:#{#filter.maxInventory} IS NULL OR COALESCE(SUM(distinct pi.quantity), 0) <= :#{#filter.maxInventory})
                AND (:#{#filter.minTransactions} IS NULL OR COUNT(distinct tp) >= :#{#filter.minTransactions})
                AND (:#{#filter.maxTransactions} IS NULL OR COUNT(distinct tp) <= :#{#filter.maxTransactions})
            """)
    Page<Object[]> findAllProducts(@Param("filter") ProductSearchFilterDto filter, Pageable pageable);

    @EntityGraph(attributePaths = {
            "productInventories",
            "productInventories.warehouse",
    })
    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    Optional<Product> findByIdWithProductInventory(@Param("productId") Integer productId);

    @EntityGraph(attributePaths = {
            "productTransactions",
            "productTransactions.transaction",
            "productTransactions.transaction.employee"
    })
    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    Optional<Product> findByIdWithTransactionProduct(@Param("productId") Integer productId);
}

//HAVING (:#{#filter.minInventory} IS NULL OR SUM(pi.quantity) >= :#{#filter.minInventory})
//AND (:#{#filter.maxInventory} IS NULL OR SUM(pi.quantity) <= :#{#filter.maxInventory})
//AND (:#{#filter.minTransactions} IS NULL OR COUNT(tp) >= :#{#filter.minTransactions})
//AND (:#{#filter.maxTransactions} IS NULL OR COUNT(tp) <= :#{#filter.maxTransactions})