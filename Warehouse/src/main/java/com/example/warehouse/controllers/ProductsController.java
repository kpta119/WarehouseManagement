package com.example.warehouse.controllers;

import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.dto.productEndpoints.ProductSearchEndpointDto;
import com.example.warehouse.mappers.products.ProductSearchEndpointMapper;
import com.example.warehouse.services.ProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductsService productsService;
    private final ProductSearchEndpointMapper productSearchEndpointMapper;


    public ProductsController(ProductsService productsService, ProductSearchEndpointMapper productSearchEndpointMapper) {
        this.productsService = productsService;
        this.productSearchEndpointMapper = productSearchEndpointMapper;
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductSearchEndpointDto>> getAllProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minSize,
            @RequestParam(required = false) Double maxSize,
            @RequestParam(required = false) Integer warehouseId
    ) {
        List<Product> products = productsService.getAllProducts(name, categoryId, minPrice, maxPrice, minSize, maxSize, warehouseId);
        List<ProductSearchEndpointDto> dtos = products.stream()
                .map(p -> productSearchEndpointMapper.mapToDto(p, warehouseId))
                .toList();
        return ResponseEntity.ok(dtos);
    }
}
