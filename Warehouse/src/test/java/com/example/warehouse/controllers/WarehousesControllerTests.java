package com.example.warehouse.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class WarehousesControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllWarehouses() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/warehouses")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.length()").value(5)
        ).andExpect(
                jsonPath("$[0].name").value("Warehouse Paris")
        ).andExpect(
                jsonPath("$[0].capacity").value(1000.0)
        ).andExpect(
                jsonPath("$[0].occupiedCapacity").value(500.0)
        ).andExpect(
                jsonPath("$[0].address").value("Champs-Élysées 101, Paris")
        ).andExpect(
                jsonPath("$[0].employeesCount").value(2)
        ).andExpect(
                jsonPath("$[0].productsCount").value(1)
        ).andExpect(
                jsonPath("$[0].transactionsCount").value(2)
        );
    }
}
