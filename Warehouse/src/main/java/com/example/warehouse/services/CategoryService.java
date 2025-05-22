package com.example.warehouse.services;

import com.example.warehouse.domain.Category;
import com.example.warehouse.domain.dto.categoryDtos.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category createCategory(CategoryDto categoryDto);

    Category updateCategory(Integer categoryId, CategoryDto categoryDto);
}
