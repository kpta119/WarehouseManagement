package com.example.warehouse.controllers;

import com.example.warehouse.domain.Category;
import com.example.warehouse.domain.dto.categoryDtos.CategoryDto;
import com.example.warehouse.mappers.CategoryMapper;
import com.example.warehouse.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoriesController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping()
    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            List<CategoryDto> dtos = categories.stream()
                    .map(categoryMapper::mapToDto)
                    .toList();
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }
}
