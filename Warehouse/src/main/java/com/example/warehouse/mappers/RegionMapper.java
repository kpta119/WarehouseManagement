package com.example.warehouse.mappers;

import com.example.warehouse.domain.Region;
import com.example.warehouse.dtos.addressDtos.RegionDto;
import org.springframework.stereotype.Component;

@Component
public class RegionMapper {
    public RegionDto mapToDto(Region region){
        RegionDto dto = new RegionDto();
        dto.setId(region.getId());
        dto.setName(region.getName());
        return dto;
    }
}
