package com.example.warehouse.controllers;

import com.example.warehouse.domain.dto.categoryDtos.CategoryDto;
import com.example.warehouse.domain.dto.filtersDto.CategorySearchFilters;
import com.example.warehouse.services.CategoryService;
import com.example.warehouse.validation.OnCreate;
import com.example.warehouse.validation.OnUpdate;
import org.springframework.dao.DataIntegrityViolationException;
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

    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllCategories(
            @ModelAttribute CategorySearchFilters filters,
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
            return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategories(filters, pageable));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createCategory(@Validated(OnCreate.class) @RequestBody CategoryDto categoryDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer categoryId, @Validated(OnUpdate.class) @RequestBody CategoryDto categoryDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategory(categoryId, categoryDto));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer categoryId) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(categoryService.deleteCategory(categoryId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete delete category because some products use it: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }
}
