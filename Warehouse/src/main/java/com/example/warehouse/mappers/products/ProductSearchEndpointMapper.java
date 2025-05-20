package com.example.warehouse.mappers.products;


import com.example.warehouse.domain.Product;
import com.example.warehouse.domain.dto.productDtos.ProductSearchEndpointDto;
import com.example.warehouse.repositories.ProductInventoryRepository;
import com.example.warehouse.repositories.TransactionProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductSearchEndpointMapper {

    private final ProductInventoryRepository inventoryRepo;
    private final TransactionProductRepository transactionRepo;

    public ProductSearchEndpointMapper(ProductInventoryRepository inventoryRepo, TransactionProductRepository transactionRepo) {
        this.inventoryRepo = inventoryRepo;
        this.transactionRepo = transactionRepo;
    }

    public ProductSearchEndpointDto mapToDto(Product product, Integer warehouseId) {

        ProductSearchEndpointDto dto = new ProductSearchEndpointDto();

        dto.setProductId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setUnitPrice(product.getUnitPrice());
        dto.setUnitSize(product.getUnitSize());

        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getName());
        }

        Integer inventoryCount;
        if (warehouseId != null) {
            inventoryCount = inventoryRepo.getInventoryCountByProductIdAndWarehouseId(product.getId(), warehouseId);
        } else {
            inventoryCount = inventoryRepo.getInventoryCountByProductId(product.getId());
        }
        dto.setInventoryCount(inventoryCount != null ? inventoryCount : 0);

        Integer transactionCount;
        if (warehouseId != null) {
            transactionCount = transactionRepo.countTransactionsByProductIdAndWarehouseId(product.getId(), warehouseId);
        } else {
            transactionCount = transactionRepo.countTransactionByProductId(product.getId());
        }
        dto.setTransactionCount(transactionCount != null ? transactionCount : 0);

        return dto;
    }
}