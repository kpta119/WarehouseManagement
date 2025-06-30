package com.example.warehouse.services;

import com.example.warehouse.domain.Category;
import com.example.warehouse.dtos.categoryDtos.CategoryDto;
import com.example.warehouse.dtos.filtersDto.CategorySearchFilters;
import com.example.warehouse.mappers.CategoryMapper;
import com.example.warehouse.repositories.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public Page<CategoryDto> getAllCategories(CategorySearchFilters filters, Pageable pageable) {
        Page<Category> categories =  categoryRepository.findAll(filters, pageable);
        return categories.map(categoryMapper::mapToDto);
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        Category createdCategory = categoryRepository.save(category);
        return categoryMapper.mapToDto(createdCategory);
    }

    public CategoryDto updateCategory(Integer categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + categoryId));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.mapToDto(updatedCategory);
    }

    public CategoryDto deleteCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + categoryId));

        categoryRepository.delete(category);
        return categoryMapper.mapToDto(category);
    }
}
