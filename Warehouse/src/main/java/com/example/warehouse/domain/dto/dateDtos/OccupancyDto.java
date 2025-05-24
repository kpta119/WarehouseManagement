package com.example.warehouse.domain.dto.dateDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OccupancyDto {
    private String date;
    private Double occupiedCapacity;
}
