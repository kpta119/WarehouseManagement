package com.example.warehouse.services;

import com.example.warehouse.domain.dto.categoryDtos.CategoryDto;
import com.example.warehouse.domain.dto.filtersDto.CategorySearchFilters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryDto> getAllCategories(CategorySearchFilters filters, Pageable pageable);

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(Integer categoryId, CategoryDto categoryDto);

    CategoryDto deleteCategory(Integer categoryId);
}
