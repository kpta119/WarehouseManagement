package com.example.warehouse.dtos.dateDtos;

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
