package com.tricol.supplier_order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.supplier_order.dto.SupplierDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SupplierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private SupplierDto createdSupplier;

    @BeforeEach
    void setUp() throws Exception {
        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setCompany("Test Supplier");
        supplierDto.setAddress("Test Address");
        supplierDto.setContact("Test Contact");
        supplierDto.setEmail("test@test.com");
        supplierDto.setPhone("123456789");
        supplierDto.setCity("Test City");
        supplierDto.setIce(12345);


        MvcResult result = mockMvc.perform(post("/api/v1/suppliers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierDto)))
                .andExpect(status().isOk())
                .andReturn();

        createdSupplier = objectMapper.readValue(result.getResponse().getContentAsString(), SupplierDto.class);
    }

    @Test
    void createSupplier() throws Exception {
        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setCompany("New Supplier");
        supplierDto.setAddress("New Address");
        supplierDto.setContact("New Contact");
        supplierDto.setEmail("new@test.com");
        supplierDto.setPhone("987654321");
        supplierDto.setCity("New City");
        supplierDto.setIce(54321);

        mockMvc.perform(post("/api/v1/suppliers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.company").value("New Supplier"))
                .andExpect(jsonPath("$.email").value("new@test.com"));
    }

    @Test
    void getSupplier_whenSupplierExists_returnsSupplier() throws Exception {
        mockMvc.perform(get("/api/v1/suppliers/" + createdSupplier.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdSupplier.getId().toString()))
                .andExpect(jsonPath("$.company").value(createdSupplier.getCompany()));
    }

    @Test
    void getSupplier_whenSupplierDoesNotExist_returnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/suppliers/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getSuppliers_returnsListOfSuppliers() throws Exception {
        mockMvc.perform(get("/api/v1/suppliers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void updateSupplier_whenSupplierExists_updatesAndReturnsSupplier() throws Exception {
        createdSupplier.setCompany("Updated Supplier Name");

        mockMvc.perform(put("/api/v1/suppliers/" + createdSupplier.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdSupplier)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdSupplier.getId().toString()))
                .andExpect(jsonPath("$.company").value("Updated Supplier Name"));
    }

    @Test
    void updateSupplier_whenSupplierDoesNotExist_returnsNotFound() throws Exception {
        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setCompany("Non-existent Supplier");
        supplierDto.setEmail("nonexistent@test.com");


        mockMvc.perform(put("/api/v1/suppliers/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSupplier_whenSupplierExists_deletesSupplier() throws Exception {
        mockMvc.perform(delete("/api/v1/suppliers/" + createdSupplier.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/suppliers/" + createdSupplier.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSupplier_whenSupplierDoesNotExist_returnsOk() throws Exception {
        mockMvc.perform(delete("/api/v1/suppliers/" + UUID.randomUUID()))
                .andExpect(status().isOk());
    }
}
