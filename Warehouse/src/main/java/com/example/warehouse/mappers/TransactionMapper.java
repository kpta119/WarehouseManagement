package com.example.warehouse.mappers;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.domain.dto.productDtos.ProductInfoDto;
import com.example.warehouse.domain.dto.transactionDtos.TransactionWithProductsDto;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public TransactionWithProductsDto mapToDto(Transaction transaction) {
        TransactionWithProductsDto dto = new TransactionWithProductsDto();

        dto.setTransactionId(transaction.getId());
        dto.setType(transaction.getTransactionType().name());
        dto.setDate(simpleDateFormat.format(transaction.getDate()));
        dto.setDescription(transaction.getDescription());

        if (transaction.getEmployee() != null) {
            dto.setEmployeeName(transaction.getEmployee().getName() + " " + transaction.getEmployee().getSurname());
            dto.setEmployeeId(transaction.getEmployee().getId());
        }

        if (transaction.getFromWarehouse() != null) {
            dto.setFromWarehouseId(transaction.getFromWarehouse().getId());
            dto.setFromWarehouseName(transaction.getFromWarehouse().getName());
        }

        if (transaction.getToWarehouse() != null) {
            dto.setToWarehouseId(transaction.getToWarehouse().getId());
            dto.setToWarehouseName(transaction.getToWarehouse().getName());
        }

        if (transaction.getClient() != null) {
            dto.setClientId(transaction.getClient().getId());
            dto.setClientName(transaction.getClient().getName());
        }

        if (transaction.getSupplier() != null) {
            dto.setSupplierId(transaction.getSupplier().getId());
            dto.setSupplierName(transaction.getSupplier().getName());
        }

        List<ProductInfoDto> productDtos = transaction.getProducts().stream()
                .map(tp -> {
                    ProductInfoDto pDto = new ProductInfoDto();
                    pDto.setProductId(tp.getProduct().getId());
                    pDto.setName(tp.getProduct().getName());
                    pDto.setQuantity(tp.getQuantity());
                    pDto.setUnitPrice(tp.getTransactionPrice());
                    pDto.setCategoryName(tp.getProduct().getCategory().getName());
                    return pDto;
                })
                .collect(Collectors.toList());
        Integer totalItems = productDtos.stream()
                        .mapToInt(ProductInfoDto::getQuantity).sum();
        dto.setTotalItems(totalItems);
        Double totalPrice = productDtos.stream()
                .mapToDouble(p -> p.getUnitPrice()*p.getQuantity()).sum();
        dto.setTotalPrice(totalPrice);
        dto.setProducts(productDtos);

        return dto;
    }

    public TransactionWithProductsDto mapToDto(Transaction transaction, List<ProductInfoDto> products, Integer totalItems, Double totalPrice) {
        TransactionWithProductsDto dto = new TransactionWithProductsDto();

        dto.setTransactionId(transaction.getId());
        dto.setType(transaction.getTransactionType().name());
        dto.setDate(simpleDateFormat.format(transaction.getDate()));
        dto.setDescription(transaction.getDescription());

        if (transaction.getEmployee() != null) {
            dto.setEmployeeName(transaction.getEmployee().getName() + " " + transaction.getEmployee().getSurname());
            dto.setEmployeeId(transaction.getEmployee().getId());
        }

        if (transaction.getFromWarehouse() != null) {
            dto.setFromWarehouseId(transaction.getFromWarehouse().getId());
            dto.setFromWarehouseName(transaction.getFromWarehouse().getName());
        }

        if (transaction.getToWarehouse() != null) {
            dto.setToWarehouseId(transaction.getToWarehouse().getId());
            dto.setToWarehouseName(transaction.getToWarehouse().getName());
        }

        if (transaction.getClient() != null) {
            dto.setClientId(transaction.getClient().getId());
            dto.setClientName(transaction.getClient().getName());
        }

        if (transaction.getSupplier() != null) {
            dto.setSupplierId(transaction.getSupplier().getId());
            dto.setSupplierName(transaction.getSupplier().getName());
        }
        dto.setTotalItems(totalItems);
        dto.setTotalPrice(totalPrice);
        dto.setProducts(products);

        return dto;
    }
}
