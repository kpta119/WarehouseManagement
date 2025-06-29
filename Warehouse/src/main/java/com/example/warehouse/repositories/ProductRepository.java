package com.example.warehouse.repositories;

import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.dto.filtersDto.ProductSearchFilterDto;
import com.example.warehouse.domain.dto.productDtos.ProductsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>, JpaRepository<Product, Integer> {
    @Query("""
                SELECT new com.example.warehouse.domain.dto.productDtos.ProductsResponseDto(
                            p.id,
                            p.name,
                            p.description,
                            p.unitPrice,
                            p.unitSize,
                            c.name,
                            COALESCE(SUM(distinct pi.quantity), 0L),
                            Count(distinct tp.id))
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
    Page<ProductsResponseDto> findAllProducts(@Param("filter") ProductSearchFilterDto filter, Pageable pageable);
}
