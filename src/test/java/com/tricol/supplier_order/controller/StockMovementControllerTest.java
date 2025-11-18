package com.tricol.supplier_order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.supplier_order.dto.CreateOrderDto;
import com.tricol.supplier_order.dto.OrderProduct;
import com.tricol.supplier_order.dto.ProductDto;
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

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class StockMovementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setCompany("Test Supplier");
        supplierDto.setEmail("supplier@test.com");
        supplierDto.setIce(12345);

        MvcResult supplierResult = mockMvc.perform(post("/api/v1/suppliers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierDto)))
                .andExpect(status().isOk())
                .andReturn();
        SupplierDto createdSupplier = objectMapper.readValue(supplierResult.getResponse().getContentAsString(), SupplierDto.class);

        ProductDto productDto = new ProductDto();
        productDto.setName("Test Product");
        productDto.setQuantity(100);
        productDto.setUnitPrice(10.0);

        MvcResult productResult = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andReturn();
        ProductDto createdProduct = objectMapper.readValue(productResult.getResponse().getContentAsString(), ProductDto.class);

        CreateOrderDto createOrderDto = new CreateOrderDto();
        createOrderDto.setSupplier(createdSupplier.getId());
        OrderProduct orderProduct = new OrderProduct(10, createdProduct.getId());
        createOrderDto.setProducts(Collections.singletonList(orderProduct));

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderDto)))
                .andExpect(status().isOk());
    }

    @Test
    void getStockMovements_returnsListOfStockMovements() throws Exception {
        mockMvc.perform(get("/api/v1/stock-movements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists());
    }
}
