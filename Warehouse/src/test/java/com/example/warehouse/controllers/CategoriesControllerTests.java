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

    @Test
    public void testUpdateCategory() throws Exception {
        String updatedCategoryJson = """
                {
                    "name": "Updated Category",
                    "description": "Updated description"
                }
                """;

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCategoryJson)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.name").value("Updated Category")
        ).andExpect(
                jsonPath("$.description").value("Updated description")
        ).andExpect(
                jsonPath("$.categoryId").value(1)
        );

        // Check if the category was actually updated in the database
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$[0].name").value("Updated Category")
        ).andExpect(
                jsonPath("$[0].description").value("Updated description")
        ).andExpect(
                jsonPath("$[0].categoryId").value(1)
        );
    }

    @Test
    public void testUpdateCategoryNotFound() throws Exception {
        String updatedCategoryJson = """
                {
                    "name": "Updated Category",
                    "description": "Updated description"
                }
                """;

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/categories/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCategoryJson)
        ).andExpect(
                status().isNotFound()
        ).andExpect(
                jsonPath("$").value("Resource not found: Category not found with id: 999")
        );
    }

    @Test
    public void testUpdateCategoryWithOnlyOneData() throws Exception {
        String invalidCategoryJson = """
                {
                    "name": "New name"
                }
                """;

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCategoryJson)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.name").value("New name")
        ).andExpect(
                jsonPath("$.description").value("Gadgets and devices")
        ).andExpect(
                jsonPath("$.categoryId").value(1)
        );

        // Check if the category was actually updated in the database

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$[0].name").value("New name")
        ).andExpect(
                jsonPath("$[0].description").value("Gadgets and devices")
        ).andExpect(
                jsonPath("$[0].categoryId").value(1)
        );
    }

    @Test
    public void testDeleteCategoryNotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/categories/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isNotFound()
        ).andExpect(
                jsonPath("$").value("Resource not found: Category not found with id: 999")
        );
    }

    @Test
    public void testDeleteCategoryConflict() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isConflict()
        );

        // Check if the category was not deleted in the database
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
    public void testDeleteCategory() throws Exception {
        this.testCreateCategory();

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/categories/6")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isNoContent()
        ).andExpect(
                jsonPath("$.name").value("New Category")
        ).andExpect(
                jsonPath("$.description").value("Description of new category")
        ).andExpect(
                jsonPath("$.categoryId").value(6)
        );

        // Check if the category was actually deleted in the database
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.length()").value(5)
        );
    }
}
