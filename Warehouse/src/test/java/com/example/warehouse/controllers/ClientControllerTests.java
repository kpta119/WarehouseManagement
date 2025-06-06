package com.example.warehouse.controllers;

import com.example.warehouse.TestDataUtil;
import com.example.warehouse.domain.Country;
import com.example.warehouse.domain.Region;
import com.example.warehouse.domain.dto.addressDtos.AddressDto;
import com.example.warehouse.domain.dto.clientAndSupplierDtos.BusinessEntityDto;
import com.example.warehouse.repositories.CountryRepository;
import com.example.warehouse.repositories.RegionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CountryRepository countryRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void testThatCreateClientReturnHttpStatus201Created() throws Exception {
        Region region = TestDataUtil.createRegion1();
        regionRepository.save(region);
        Country country = TestDataUtil.createCountry1(region);
        countryRepository.save(country);
        AddressDto addressDto = TestDataUtil.createAddressDto1(country);
        BusinessEntityDto businessEntityDto = TestDataUtil.createClientDto1(addressDto);

        String createClientAsJson = objectMapper.writeValueAsString(businessEntityDto);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createClientAsJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }
}
