package com.example.warehouse.controllers;

import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.dto.dateDtos.Period;
import com.example.warehouse.domain.dto.productDtos.ProductDataBaseDto;
import com.example.warehouse.domain.dto.productDtos.ProductSearchEndpointDto;
import com.example.warehouse.domain.dto.transactionDtos.TransactionDto;
import com.example.warehouse.mappers.ProductMapper;
import com.example.warehouse.services.ProductsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductsService productsService;
    private final ProductMapper productMapper;


    public ProductsController(ProductsService productsService, ProductMapper productMapper) {
        this.productsService = productsService;
        this.productMapper = productMapper;
    }

    @GetMapping("/search")
    public ResponseEntity<?> getAllProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minSize,
            @RequestParam(required = false) Double maxSize,
            @RequestParam(required = false) Integer warehouseId
    ) {
        try {
            List<Product> products = productsService.getAllProducts(name, categoryId, minPrice, maxPrice, minSize, maxSize, warehouseId);
            List<ProductSearchEndpointDto> dtos = products.stream()
                    .map(p -> productMapper.mapToDto(p,
                            productsService.getInventoryCount(p.getId(), warehouseId),
                            productsService.getTransactionCount(p.getId(), warehouseId)))
                    .toList();
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Integer productId) {
        try {
            Product product = productsService.getProductById(productId);
            Map<Integer, Integer> inventory = productsService.getInventoryMap(productId);
            List<TransactionDto> transactions = productsService.getTransactionsDto(productId);
            return ResponseEntity.ok(productMapper.mapToDto(product, inventory, transactions));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @GetMapping("/low-stock")
    public ResponseEntity<?> getLowStockProducts(@RequestParam(required = false) Integer warehouseId) {
        try {
            int lowStockThreshold = 5;
            List<Integer> lowStockProductIds = productsService.getLowStockProductIds(warehouseId, lowStockThreshold);
            return ResponseEntity.ok(lowStockProductIds);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @GetMapping("/best-selling")
    public ResponseEntity<?> getTop3BestSellingProducts(@RequestParam(required = false) Integer warehouseId, @RequestParam String period) {
        try {
            Period parsedPeriod = Period.valueOf(period.toLowerCase());
            int topN = 3;
            List<Integer> products = productsService.getBestSellingProducts(warehouseId, parsedPeriod, topN);
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid period argument: " + period);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDataBaseDto product, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            error -> Objects.toString(error.getDefaultMessage(), "Invalid value")
                    ));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            Product savedProduct = productsService.createProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.mapToDto(savedProduct));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }
}
