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

    @Test
    public void testCreateCategory() throws Exception {
        String newCategoryJson = """
                {
                    "name": "New Category",
                    "description": "Description of new category"
                }
                """;

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCategoryJson)
        ).andExpect(
                status().isCreated()
        ).andExpect(
                jsonPath("$.name").value("New Category")
        ).andExpect(
                jsonPath("$.description").value("Description of new category")
        ).andExpect(
                jsonPath("$.categoryId").value(6)
        );

        // Check if the category was actually created in the database
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.length()").value(6)
        ).andExpect(
                jsonPath("$[5].name").value("New Category")
        ).andExpect(
                jsonPath("$[5].description").value("Description of new category")
        ).andExpect(
                jsonPath("$[5].categoryId").value(6)
        );
    }

    @Test
    public void testCreateCategoryWithValidationErrors() throws Exception {
        String invalidCategoryJson = """
                {
                    "name": ""
                }
                """;

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCategoryJson)
        ).andExpect(
                status().isBadRequest()
        ).andExpect(
                jsonPath("$.name").value("Name cannot be blank")
        ).andExpect(
                jsonPath("$.description").value("Description cannot be blank")
        );
    }
}
