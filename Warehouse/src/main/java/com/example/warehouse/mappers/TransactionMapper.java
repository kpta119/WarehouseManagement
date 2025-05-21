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
            dto.setEmployeeId(transaction.getEmployee().getId());
        }

        if (transaction.getFromWarehouse() != null) {
            dto.setFromWarehouseId(transaction.getFromWarehouse().getId());
        }

        if (transaction.getToWarehouse() != null) {
            dto.setToWarehouseId(transaction.getToWarehouse().getId());
        }

        if (transaction.getClient() != null) {
            dto.setClientId(transaction.getClient().getId());
        }

        if (transaction.getSupplier() != null) {
            dto.setSupplierId(transaction.getSupplier().getId());
        }

        List<ProductInfoDto> productDtos = transaction.getProducts().stream()
                .map(tp -> {
                    ProductInfoDto pDto = new ProductInfoDto();
                    pDto.setProductId(tp.getProduct().getId());
                    pDto.setName(tp.getProduct().getName());
                    pDto.setQuantity(tp.getQuantity());
                    pDto.setUnitPrice(tp.getTransactionPrice()); // albo tp.getProduct().getUnitPrice()
                    pDto.setCategoryName(tp.getProduct().getCategory().getName());
                    return pDto;
                })
                .collect(Collectors.toList());

        dto.setProducts(productDtos);

        return dto;
    }
}
