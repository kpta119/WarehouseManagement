package com.example.warehouse.mappers;

import com.example.warehouse.domain.dto.warehouseDto.WarehouseGetAllEndpointDto;
import org.springframework.stereotype.Component;

@Component
public class WarehousesMapper {

    public WarehouseGetAllEndpointDto mapToDto(Object[] warehouse) {
        WarehouseGetAllEndpointDto dto = new WarehouseGetAllEndpointDto();
        dto.setWarehouseId(((Number) warehouse[0]).intValue());
        dto.setName((String) warehouse[1]);
        dto.setCapacity((Double) warehouse[2]);
        dto.setOccupiedCapacity((double) warehouse[3]);
        dto.setAddress(
                warehouse[4] + " " + warehouse[5] + ", " + warehouse[6]
        );
        dto.setEmployeesCount(((Number) warehouse[7]).intValue());
        dto.setProductsCount(((Number) warehouse[8]).intValue());
        dto.setTransactionsCount(((Number) warehouse[9]).intValue());
        return dto;
    }
}
