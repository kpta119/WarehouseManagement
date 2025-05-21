package com.example.warehouse.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testReturnsAllProducts() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/search")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(6)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Laptop")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].unitPrice").value(999.99)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].inventoryCount").value(5)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].transactionCount").value(2)
        );
    }

    @Test
    public void testReturnsProductsByName() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/search")
                        .param("name", "Laptop")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Laptop")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].unitPrice").value(999.99)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].inventoryCount").value(5)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].transactionCount").value(2)
        );
    }

    @Test
    public void testReturnsProductsByCategoryId() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/search")
                        .param("categoryId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].name").value("Smartphone")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].unitPrice").value(799.99)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].inventoryCount").value(0)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].transactionCount").value(0)
        );
    }

    @Test
    public void testReturnsProductsInCorrectPriceBracket() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/search")
                        .param("minPrice", "30")
                        .param("maxPrice", "800")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Chair")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].unitPrice").value(49.99)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].inventoryCount").value(3)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].transactionCount").value(1)
        );
    }

    @Test
    public void testReturnsProductsInCorrectSizeBracket() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/search")
                        .param("minSize", "0")
                        .param("maxSize", "0.5")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(3)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("T-Shirt")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].unitPrice").value(19.99)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].inventoryCount").value(10)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].transactionCount").value(1)
        );
    }

    @Test
    public void testReturnsProductsByWarehouseId() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/search")
                        .param("warehouseId", "2")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("T-Shirt")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].unitPrice").value(19.99)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].inventoryCount").value(6)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].transactionCount").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].name").value("Laptop")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].unitPrice").value(999.99)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].inventoryCount").value(3)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].transactionCount").value(1)
        );
    }

    @Test
    public void testReturnsSingleProduct() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Laptop")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.categoryName").value("Electronics")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.inventory.length()").value(2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.inventory['1']").value(2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.inventory['2']").value(3)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.transactions.length()").value(2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.transactions[0].transactionId").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.transactions[0].quantity").value(10)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.transactions[1].transactionId").value(2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.transactions[1].quantity").value(5)
        );
    }

    @Test
    public void testReturnsProductIdsWithLowStock() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/low-stock")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0]").value(4)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1]").value(5)
        );
    }

    @Test
    public void restReturnsProductIdsWithLowStockInDefinedWarehouse() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/low-stock")
                        .param("warehouseId", "2")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0]").value(1)
        );
    }

    @Test
    public void testGetTop3BestSellingErrorReturnsBadPeriodName() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/best-selling")
                        .param("period", "moonth")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void testGetTop3BestSellingPeriodWeek() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/best-selling")
                        .param("period", "week")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0]").value(4)
        );
    }

    @Test
    public void testGetTop3BestSellingPeriodMonth() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/best-selling")
                        .param("period", "month")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0]").value(3)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1]").value(4)
        );
    }

    @Test
    public void testGetTop3BestSellingPeriodYear() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/best-selling")
                        .param("period", "year")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(3)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0]").value(3)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1]").value(4)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[2]").value(1)
        );
    }

    @Test
    public void testGetTop3BestSellingPeriodYearWithWarehouseId() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/best-selling")
                        .param("period", "year")
                        .param("warehouseId", "2")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0]").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1]").value(2)
        );
    }

    @Test
    public void testGetTop3BestSellingWarehouseToWarehouseTransactionFromWarehouseId() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/best-selling")
                        .param("period", "month")
                        .param("warehouseId", "4")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0]").value(4)
        );
    }

    @Test
    public void testGetTop3BestSellingWarehouseToWarehouseTransactionToWarehouseId() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/best-selling")
                        .param("period", "month")
                        .param("warehouseId", "5")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0]").value(4)
        );
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Profile("test")
        public Clock clock() {
            return Clock.fixed(Instant.parse("2025-05-21T00:00:00Z"), ZoneId.of("UTC"));
        }
    }


}
