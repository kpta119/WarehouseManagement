package com.example.warehouse.services;

import com.example.warehouse.domain.dto.addressDtos.RegionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegionService {
    List<RegionDto> getAllRegions();
}
