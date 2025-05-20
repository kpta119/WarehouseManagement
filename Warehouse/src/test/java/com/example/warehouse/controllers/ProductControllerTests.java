package com.example.warehouse.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
                MockMvcResultMatchers.jsonPath("$[0].inventoryCount").value(70)
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
                MockMvcResultMatchers.jsonPath("$[0].inventoryCount").value(70)
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
                MockMvcResultMatchers.jsonPath("$[0].inventoryCount").value(100)
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
                MockMvcResultMatchers.jsonPath("$[0].inventoryCount").value(350)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].transactionCount").value(0)
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
                MockMvcResultMatchers.jsonPath("$[0].inventoryCount").value(200)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].transactionCount").value(0)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].name").value("Laptop")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].unitPrice").value(999.99)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].inventoryCount").value(20)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].transactionCount").value(1)
        );
    }


}
