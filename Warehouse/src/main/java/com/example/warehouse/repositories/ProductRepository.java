package com.example.warehouse.repositories;

import com.example.warehouse.domain.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>, JpaRepository<Product, Integer> {
    @Query("""
                SELECT p.id, p.name, p.description, p.unitPrice, p.unitSize,
                       (
                            SELECT COALESCE(SUM(pi2.quantity),0)
                            FROM ProductInventory pi2
                            WHERE pi2.product.id = p.id
                            AND (:warehouseId IS NULL OR pi2.warehouse.id = :warehouseId)
                       ) AS totalInventory,
                       (
                            SELECT COUNT(tp2)
                            FROM TransactionProduct tp2
                            WHERE tp2.product.id = p.id
                            AND (:warehouseId IS NULL OR tp2.transaction.fromWarehouse.id = :warehouseId OR tp2.transaction.toWarehouse.id = :warehouseId)
                       ) AS totalTransactions,
                       c.name AS categoryName
                FROM Product p
                LEFT JOIN p.category c
                LEFT JOIN ProductInventory pi ON p.id = pi.product.id
                WHERE (:warehouseId IS NULL OR pi.warehouse.id = :warehouseId)
                AND (:name IS NULL OR p.name LIKE CONCAT('%', :name, '%'))
                AND (:categoryId IS NULL OR p.category.id = :categoryId)
                AND (:minPrice IS NULL OR p.unitPrice >= :minPrice)
                AND (:maxPrice IS NULL OR p.unitPrice <= :maxPrice)
                AND (:minSize IS NULL OR p.unitSize >= :minSize)
                AND (:maxSize IS NULL OR p.unitSize <= :maxSize)
                GROUP BY p.id
            """)
    List<Object[]> findAllProducts(@Param("name") String name,
                                   @Param("categoryId") Integer categoryId,
                                   @Param("minPrice") Double minPrice,
                                   @Param("maxPrice") Double maxPrice,
                                   @Param("minSize") Double minSize,
                                   @Param("maxSize") Double maxSize,
                                   @Param("warehouseId") Integer warehouseId);

    @EntityGraph(attributePaths = {
            "productInventories",
            "productInventories.warehouse",
    })
    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    Optional<Product> findByIdWithProductInventory(@Param("productId") Integer productId);

    @EntityGraph(attributePaths = {
            "productTransactions.transaction",
            "productTransactions"
    })
    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    Optional<Product> findByIdWithTransactionProduct(@Param("productId") Integer productId);
}
