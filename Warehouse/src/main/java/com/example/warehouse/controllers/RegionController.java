package com.example.warehouse.controllers;

import com.example.warehouse.domain.dto.addressDtos.RegionDto;
import com.example.warehouse.services.RegionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllRegions(){
        try {
            List<RegionDto> allRegions = regionService.getAllRegions();
            return ResponseEntity.status(HttpStatus.OK).body(allRegions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }
}
