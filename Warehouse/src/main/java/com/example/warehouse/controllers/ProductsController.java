package com.example.warehouse.controllers;

import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.dto.dateDtos.Period;
import com.example.warehouse.domain.dto.filtersDto.ProductSearchFilterDto;
import com.example.warehouse.domain.dto.productDtos.ProductDataBaseDto;
import com.example.warehouse.domain.dto.productDtos.ProductSearchEndpointDto;
import com.example.warehouse.domain.dto.transactionDtos.ProductTransactionInfoDto;
import com.example.warehouse.mappers.ProductMapper;
import com.example.warehouse.services.ProductsService;
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

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductsService productsService;
    private final ProductMapper productMapper;
    private final int lowStockThreshold = 5;
    private final int topN = 3;


    public ProductsController(ProductsService productsService, ProductMapper productMapper) {
        this.productsService = productsService;
        this.productMapper = productMapper;
    }

    @GetMapping("/search")
    public ResponseEntity<?> getAllProducts(
            @ModelAttribute ProductSearchFilterDto productFilters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "false") boolean all
    ) {
        try {
            Page<Object[]> productsWithInventory;
            if (all) {
                productsWithInventory = productsService.getAllProducts(productFilters, Pageable.unpaged());
            } else {
                productsWithInventory = productsService.getAllProducts(productFilters, PageRequest.of(page, size));
            }
            List<Integer> lowStockProductIds = productsService.getLowStockProductIds(productFilters.getWarehouseId(), lowStockThreshold);
            List<Integer> bestSellingProducts = productsService.getBestSellingProducts(productFilters.getWarehouseId(), Period.year, topN);
            Page<ProductSearchEndpointDto> dtos = productsWithInventory.map(product -> productMapper.mapToDto(
                    product,
                    lowStockProductIds,
                    bestSellingProducts
            ));
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Integer productId) {
        try {
            Product product = productsService.getProductByIdWithProductInventory(productId);
            Map<Integer, Integer> inventory = productsService.getInventoryMap(product);
            List<ProductTransactionInfoDto> transactions = productsService.getTransactionsDto(productId);
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
            List<Integer> lowStockProductIds = productsService.getLowStockProductIds(warehouseId, lowStockThreshold);
            return ResponseEntity.ok(lowStockProductIds);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @GetMapping("/best-selling")
    public ResponseEntity<?> getTop3BestSellingProducts(@RequestParam(required = false) Integer warehouseId, @RequestParam String period) {
        try {
            Period parsedPeriod = Period.valueOf(period);
            List<Integer> products = productsService.getBestSellingProducts(warehouseId, parsedPeriod, topN);
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid period argument: " + period);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createProduct(@Validated(OnCreate.class) @RequestBody ProductDataBaseDto product) {
        try {
            Product savedProduct = productsService.createProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.mapToDto(savedProduct));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer productId, @Validated(OnUpdate.class) @RequestBody ProductDataBaseDto product) {
        try {
            Product updatedProduct = productsService.updateProduct(productId, product);
            return ResponseEntity.status(HttpStatus.OK).body(productMapper.mapToDto(updatedProduct));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        try {
            Product deletedProduct = productsService.deleteProduct(productId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(productMapper.mapToDto(deletedProduct));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete delete product because it is in a history of transactions or in warehouse inventory: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }
}
