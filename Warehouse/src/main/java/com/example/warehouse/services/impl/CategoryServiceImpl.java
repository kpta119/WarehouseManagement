package com.example.warehouse.services.impl;

import com.example.warehouse.domain.Category;
import com.example.warehouse.domain.dto.categoryDtos.CategoryDto;
import com.example.warehouse.domain.dto.filtersDto.CategorySearchFilters;
import com.example.warehouse.mappers.CategoryMapper;
import com.example.warehouse.repositories.CategoryRepository;
import com.example.warehouse.services.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Page<CategoryDto> getAllCategories(CategorySearchFilters filters, Pageable pageable) {
        Page<Category> categories =  categoryRepository.findAll(filters, pageable);
        return categories.map(categoryMapper::mapToDto);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        categoryRepository.save(category);
        return categoryMapper.mapToDto(category);
    }

    @Override
    public CategoryDto updateCategory(Integer categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + categoryId));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        categoryRepository.save(category);
        return categoryMapper.mapToDto(category);
    }

    @Override
    public CategoryDto deleteCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + categoryId));

        categoryRepository.delete(category);
        return categoryMapper.mapToDto(category);
    }
}
