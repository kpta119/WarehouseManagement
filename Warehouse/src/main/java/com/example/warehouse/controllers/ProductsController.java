package com.example.warehouse.controllers;

import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.dto.transactionDtos.TransactionDto;
import com.example.warehouse.domain.dto.productDtos.ProductSearchEndpointDto;
import com.example.warehouse.mappers.products.ProductGetSingleProductMapper;
import com.example.warehouse.mappers.products.ProductSearchEndpointMapper;
import com.example.warehouse.services.ProductsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductsService productsService;
    private final ProductSearchEndpointMapper productSearchEndpointMapper;
    private final ProductGetSingleProductMapper productGetSingleProductMapper;


    public ProductsController(ProductsService productsService, ProductSearchEndpointMapper productSearchEndpointMapper, ProductGetSingleProductMapper productGetSingleProductMapper) {
        this.productsService = productsService;
        this.productSearchEndpointMapper = productSearchEndpointMapper;
        this.productGetSingleProductMapper = productGetSingleProductMapper;
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
                    .map(p -> productSearchEndpointMapper.mapToDto(p,
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
            return ResponseEntity.ok(productGetSingleProductMapper.mapToDto(product, inventory, transactions));
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
}
