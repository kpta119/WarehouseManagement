package com.example.warehouse.controllers;

import com.example.warehouse.domain.Category;
import com.example.warehouse.domain.dto.categoryDtos.CategoryDto;
import com.example.warehouse.mappers.CategoryMapper;
import com.example.warehouse.services.CategoryService;
import com.example.warehouse.validation.OnCreate;
import com.example.warehouse.validation.OnUpdate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

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
    public ResponseEntity<?> getAllCategories(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "25") Integer size,
            @RequestParam(defaultValue = "false") boolean all
    ) {
        try {
            Pageable pageable;
            if (all) {
                pageable = Pageable.unpaged();
            } else {
                pageable = PageRequest.of(page, size);
            }
            Page<Category> categories = categoryService.getAllCategories(pageable);
            Page<CategoryDto> dtos = categories.map(categoryMapper::mapToDto);
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createCategory(@Validated(OnCreate.class) @RequestBody CategoryDto categoryDto) {
        try {
            Category savedCategory = categoryService.createCategory(categoryDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryMapper.mapToDto(savedCategory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer categoryId, @Validated(OnUpdate.class) @RequestBody CategoryDto categoryDto) {
        try {
            Category updatedCategory = categoryService.updateCategory(categoryId, categoryDto);
            return ResponseEntity.ok(categoryMapper.mapToDto(updatedCategory));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer categoryId) {
        try {
            Category category = categoryService.deleteCategory(categoryId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(categoryMapper.mapToDto(category));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete delete category because some products use it: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }
}
