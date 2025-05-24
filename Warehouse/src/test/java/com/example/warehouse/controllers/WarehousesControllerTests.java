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

    @Test
    public void testCreateWarehouseValidationError() throws Exception {
        String invalidProductJson = """
                {
                    "capacity": 0,
                    "regionId": 1,
                    "city": "Paris",
                    "postalCode": "75001",
                    "street": "",
                    "streetNumber": "101"
                }
                """;

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/warehouses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidProductJson)
        ).andExpect(
                status().isBadRequest()
        ).andExpect(
                jsonPath("$.name").value("Name cannot be blank")
        ).andExpect(
                jsonPath("$.capacity").value("Capacity must be positive")
        ).andExpect(
                jsonPath("$.street").value("Street cannot be blank")
        );
    }

    @Test
    public void testCreateWarehouseCountryNotFound() throws Exception {
        String newWarehouseJson = """
                {
                    "name": "New Warehouse",
                    "capacity": 1500.0,
                    "regionId": 1,
                    "countryId": 1999,
                    "city": "Paris",
                    "postalCode": "75001",
                    "street": "Champs-Élysées",
                    "streetNumber": "102"
                }
                """;

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/warehouses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newWarehouseJson)
        ).andExpect(
                status().isNotFound()
        ).andExpect(
                jsonPath("$").value("Resource not found: Country not found with ID: 1999")
        );
    }

    @Test
    public void testCreateWarehouse() throws Exception {
        String newWarehouseJson = """
                {
                    "name": "New Warehouse",
                    "capacity": 1500.0,
                    "regionId": 1,
                    "countryId": 1,
                    "city": "Paris",
                    "postalCode": "75001",
                    "street": "Champs-Élysées",
                    "streetNumber": "102"
                }
                """;

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/warehouses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newWarehouseJson)
        ).andExpect(
                status().isCreated()
        ).andExpect(
                jsonPath("$.warehouseId").value(6)
        ).andExpect(
                jsonPath("$.name").value("New Warehouse")
        ).andExpect(
                jsonPath("$.capacity").value(1500.0)
        ).andExpect(
                jsonPath("$.occupiedCapacity").value(0.0)
        ).andExpect(
                jsonPath("$.addressId").value(6)
        );

        // Verify the warehouse was created in the database

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/warehouses/6")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.warehouseId").value(6)
        ).andExpect(
                jsonPath("$.name").value("New Warehouse")
        ).andExpect(
                jsonPath("$.capacity").value(1500.0)
        ).andExpect(
                jsonPath("$.occupiedCapacity").value(0.0)
        ).andExpect(
                jsonPath("$.address").value("Champs-Élysées 102, Paris")
        );
    }

    @Test
    public void testUpdateWarehouseValidationError() throws Exception {
        String invalidWarehouseJson = """
                {
                    "name": "New Warehouse",
                    "capacity": -100,
                    "regionId": 1,
                    "countryId": -2
                }
                """;

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/warehouses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidWarehouseJson)
        ).andExpect(
                status().isBadRequest()
        ).andExpect(
                jsonPath("$.capacity").value("Capacity must be positive")
        ).andExpect(
                jsonPath("$.countryId").value("CountryId must be positive")
        );
    }

    @Test
    public void testUpdateWarehouseNotFound() throws Exception {
        String updateWarehouseJson = """
                {
                    "name": "Updated Warehouse",
                    "capacity": 2000.0,
                    "regionId": 1,
                    "countryId": 1,
                    "city": "Paris",
                    "postalCode": "75001",
                    "street": "Champs-Élysées",
                    "streetNumber": "103"
                }
                """;

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/warehouses/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateWarehouseJson)
        ).andExpect(
                status().isNotFound()
        ).andExpect(
                jsonPath("$").value("Warehouse not found: Warehouse not found with ID: 999")
        );
    }

    @Test
    public void testUpdateWarehouseChangeOfNameCapacityAndStreet() throws Exception {
        String updateWarehouseJson = """
                {
                    "name": "Updated Warehouse",
                    "capacity": 2000.0,
                    "street": "Champs-Élysées",
                    "streetNumber": "103"
                }
                """;

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/warehouses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateWarehouseJson)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.warehouseId").value(1)
        ).andExpect(
                jsonPath("$.name").value("Updated Warehouse")
        ).andExpect(
                jsonPath("$.capacity").value(2000.0)
        ).andExpect(
                jsonPath("$.addressId").value(1)
        );

        // Verify the warehouse was updated in the database
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/warehouses/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.name").value("Updated Warehouse")
        ).andExpect(
                jsonPath("$.capacity").value(2000.0)
        ).andExpect(
                jsonPath("$.address").value("Champs-Élysées 103, Paris")
        );
    }

    @Test
    public void testUpdateWarehouseChangeAddressNewCityNameAndPostCode() throws Exception {
        String updateWarehouseJson = """
                {
                    "city": "Lyon",
                    "postalCode": "69001",
                    "countryId": 1
                }
                """;

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/warehouses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateWarehouseJson)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.warehouseId").value(1)
        ).andExpect(
                jsonPath("$.addressId").value(1)
        );

        // Verify the address was updated in the database
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/warehouses/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.address").value("Champs-Élysées 101, Lyon")
        );
    }

    @Test
    public void testUpdateWarehouseChangeAddressCityExists() throws Exception {
        String updateWarehouseJson = """
                {
                    "city": "New York",
                    "postalCode": "10001",
                    "countryId": 3
                }
                """;

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/warehouses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateWarehouseJson)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.warehouseId").value(1)
        ).andExpect(
                jsonPath("$.addressId").value(1)
        );

        // Verify the address was updated in the database
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/warehouses/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                jsonPath("$.address").value("Champs-Élysées 101, New York")
        );
    }

    @Test
    public void testDeleteWarehouseNotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/warehouses/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isNotFound()
        ).andExpect(
                jsonPath("$").value("Warehouse not found: Warehouse not found with ID: 999")
        );
    }

    @Test
    public void testDeleteWarehouseDeleteUsedWarehouse() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/warehouses/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isConflict()
        );
    }

    @Test
    public void testDeleteWarehouse() throws Exception {
        this.testCreateWarehouse();

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/warehouses/6")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isNoContent()
        );

        // Verify the warehouse was deleted from the database
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/warehouses/6")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isNotFound()
        ).andExpect(
                jsonPath("$").value("Warehouse not found: Warehouse not found with ID: 6")
        );
    }


}
