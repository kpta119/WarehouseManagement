package com.example.warehouse.services.impl;

import com.example.warehouse.domain.Region;
import com.example.warehouse.domain.dto.addressDtos.RegionDto;
import com.example.warehouse.mappers.RegionMapper;
import com.example.warehouse.repositories.RegionRepository;
import com.example.warehouse.services.RegionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {
    private final RegionMapper regionMapper;
    private final RegionRepository regionRepository;

    public RegionServiceImpl(RegionMapper regionMapper, RegionRepository regionRepository) {
        this.regionMapper = regionMapper;
        this.regionRepository = regionRepository;
    }

    public List<RegionDto> getAllRegions(){
        Iterable<Region> regions = regionRepository.findAll();
        List<RegionDto> regionDtos = new ArrayList<>();

        for (Region region : regions) {
            RegionDto dto = regionMapper.mapToDto(region);
            regionDtos.add(dto);
        }

        return regionDtos;
    }
}
