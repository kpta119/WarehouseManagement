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
public class CategoriesControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllCategories() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.length()").value(5)
        ).andExpect(
                jsonPath("$[0].name").value("Electronics")
        ).andExpect(
                jsonPath("$[0].description").value("Gadgets and devices")
        ).andExpect(
                jsonPath("$[0].categoryId").value(1)
        );
    }
}
