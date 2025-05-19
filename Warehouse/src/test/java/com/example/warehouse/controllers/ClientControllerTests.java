package com.example.warehouse.controllers;

import com.example.warehouse.TestDataUtil;
import com.example.warehouse.domain.Country;
import com.example.warehouse.domain.Region;
import com.example.warehouse.domain.dto.AddressDto;
import com.example.warehouse.domain.dto.ClientDto;
import com.example.warehouse.repositories.CountryRepository;
import com.example.warehouse.repositories.RegionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ClientControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CountryRepository countryRepository;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void testThatCreateClientReturnHttpStatus201Created() throws Exception{
        Region region = TestDataUtil.createRegion1();
        regionRepository.save(region);
        Country country = TestDataUtil.createCountry1(region);
        countryRepository.save(country);
        AddressDto addressDto = TestDataUtil.createAddressDto1(country);
        ClientDto clientDto = TestDataUtil.createClientDto1(addressDto);

        String createClientAsJson = objectMapper.writeValueAsString(clientDto);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createClientAsJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }
}
