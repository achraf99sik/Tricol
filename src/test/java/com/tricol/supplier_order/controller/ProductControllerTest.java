package com.tricol.supplier_order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.supplier_order.dto.ProductDto;
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
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDto createdProduct;

    @BeforeEach
    void setUp() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setName("Test Product");
        productDto.setDescription("Test Description");
        productDto.setQuantity(10);
        productDto.setUnitPrice(100.0);
        productDto.setCategory("Test Category");


        MvcResult result = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andReturn();

        createdProduct = objectMapper.readValue(result.getResponse().getContentAsString(), ProductDto.class);
    }

    @Test
    void createProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setName("New Product");
        productDto.setDescription("New Description");
        productDto.setQuantity(20);
        productDto.setUnitPrice(200.0);
        productDto.setCategory("New Category");

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("New Product"))
                .andExpect(jsonPath("$.description").value("New Description"))
                .andExpect(jsonPath("$.quantity").value(20))
                .andExpect(jsonPath("$.unitPrice").value(200.0))
                .andExpect(jsonPath("$.category").value("New Category"));
    }

    @Test
    void getProduct_whenProductExists_returnsProduct() throws Exception {
        mockMvc.perform(get("/api/v1/products/" + createdProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdProduct.getId().toString()))
                .andExpect(jsonPath("$.name").value(createdProduct.getName()));
    }

    @Test
    void getProduct_whenProductDoesNotExist_returnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/products/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getProducts_returnsListOfProducts() throws Exception {
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void updateProduct_whenProductExists_updatesAndReturnsProduct() throws Exception {
        createdProduct.setName("Updated Product Name");

        mockMvc.perform(put("/api/v1/products/" + createdProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdProduct.getId().toString()))
                .andExpect(jsonPath("$.name").value("Updated Product Name"));
    }

    @Test
    void updateProduct_whenProductDoesNotExist_returnsNotFound() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setName("Non-existent Product");

        mockMvc.perform(put("/api/v1/products/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_whenProductExists_deletesProduct() throws Exception {
        mockMvc.perform(delete("/api/v1/products/" + createdProduct.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/products/" + createdProduct.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_whenProductDoesNotExist_returnsOk() throws Exception {
        mockMvc.perform(delete("/api/v1/products/" + UUID.randomUUID()))
                .andExpect(status().isOk());
    }
}
