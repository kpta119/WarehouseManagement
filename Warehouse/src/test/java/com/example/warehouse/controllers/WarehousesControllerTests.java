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

    @Test
    public void testGetWarehouseByIdNotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/warehouses/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isNotFound()
        ).andExpect(
                jsonPath("$").value("Warehouse not found: Warehouse not found with ID: 999")
        );
    }

    @Test
    public void testGetWarehouseById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/warehouses/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.warehouseId").value(1)
        ).andExpect(
                jsonPath("$.name").value("Warehouse Paris")
        ).andExpect(
                jsonPath("$.capacity").value(1000.0)
        ).andExpect(
                jsonPath("$.occupiedCapacity").value(500.0)
        ).andExpect(
                jsonPath("$.address").value("Champs-Élysées 101, Paris")
        ).andExpect(
                jsonPath("$.employees.length()").value(2)
        ).andExpect(
                jsonPath("$.employees[0].employeeId").value(1)
        ).andExpect(
                jsonPath("$.products.length()").value(1)
        ).andExpect(
                jsonPath("$.products[0].productId").value(1)
        ).andExpect(
                jsonPath("$.products[0].quantity").value(2)
        ).andExpect(
                jsonPath("$.transactions.length()").value(2)
        ).andExpect(
                jsonPath("$.transactions[0].transactionId").value(1)
        ).andExpect(
                jsonPath("$.transactions[0].date").value("2024-06-01")
        ).andExpect(
                jsonPath("$.transactions[0].type").value("WAREHOUSE_TO_WAREHOUSE")
        ).andExpect(
                jsonPath("$.transactions[0].totalPrice").value(11799.9)
        ).andExpect(
                jsonPath("$.occupancyHistory.length()").value(2)
        ).andExpect(
                jsonPath("$.occupancyHistory[0].date").value("2024-05-31")
        ).andExpect(
                jsonPath("$.occupancyHistory[0].occupiedCapacity").value(550.0)
        ).andExpect(
                jsonPath("$.occupancyHistory[1].date").value("2024-06-01")
        ).andExpect(
                jsonPath("$.occupancyHistory[1].occupiedCapacity").value(450)
        );
    }
}
