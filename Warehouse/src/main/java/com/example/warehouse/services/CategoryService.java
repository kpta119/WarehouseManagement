package com.example.warehouse.services;

import com.example.warehouse.domain.Category;
import com.example.warehouse.domain.dto.categoryDtos.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<Category> getAllCategories(Pageable pageable);

    Category createCategory(CategoryDto categoryDto);

    Category updateCategory(Integer categoryId, CategoryDto categoryDto);

    Category deleteCategory(Integer categoryId);
}
