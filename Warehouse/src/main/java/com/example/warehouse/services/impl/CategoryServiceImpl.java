package com.example.warehouse.services.impl;

import com.example.warehouse.domain.Category;
import com.example.warehouse.domain.dto.categoryDtos.CategoryDto;
import com.example.warehouse.domain.dto.filtersDto.CategorySearchFilters;
import com.example.warehouse.repositories.CategoryRepository;
import com.example.warehouse.services.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<Category> getAllCategories(CategorySearchFilters filters, Pageable pageable) {
        return categoryRepository.findAll(filters, pageable);
    }

    @Override
    public Category createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Integer categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + categoryId));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return categoryRepository.save(category);
    }

    @Override
    public Category deleteCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + categoryId));

        categoryRepository.delete(category);
        return category;
    }
}
