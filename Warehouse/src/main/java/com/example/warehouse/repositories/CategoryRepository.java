package com.example.warehouse.repositories;

import com.example.warehouse.domain.Category;
import com.example.warehouse.dtos.filtersDto.CategorySearchFilters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("""
        SELECT c FROM Category c
        WHERE (:#{#filters.name} IS NULL OR c.name LIKE %:#{#filters.name}%)
""")
    Page<Category> findAll(CategorySearchFilters filters, Pageable pageable);
}
