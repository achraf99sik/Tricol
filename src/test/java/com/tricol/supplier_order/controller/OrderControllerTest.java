package com.tricol.supplier_order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.supplier_order.dto.CreateOrderDto;
import com.tricol.supplier_order.dto.OrderProduct;
import com.tricol.supplier_order.dto.ProductDto;
import com.tricol.supplier_order.dto.SupplierDto;
import com.tricol.supplier_order.dto.SupplierOrderDto;
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
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private SupplierDto createdSupplier;
    private ProductDto createdProduct;
    private SupplierOrderDto createdOrder;

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
        createdSupplier = objectMapper.readValue(supplierResult.getResponse().getContentAsString(), SupplierDto.class);

        ProductDto productDto = new ProductDto();
        productDto.setName("Test Product");
        productDto.setQuantity(100);
        productDto.setUnitPrice(10.0);


        MvcResult productResult = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andReturn();
        createdProduct = objectMapper.readValue(productResult.getResponse().getContentAsString(), ProductDto.class);

        CreateOrderDto createOrderDto = new CreateOrderDto();
        createOrderDto.setSupplier(createdSupplier.getId());
        OrderProduct orderProduct = new OrderProduct(10, createdProduct.getId());
        createOrderDto.setProducts(Collections.singletonList(orderProduct));

        MvcResult orderResult = mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderDto)))
                .andExpect(status().isOk())
                .andReturn();
        createdOrder = objectMapper.readValue(orderResult.getResponse().getContentAsString(), SupplierOrderDto.class);
    }

    @Test
    void createSupplierOrder() throws Exception {
        CreateOrderDto createOrderDto = new CreateOrderDto();
        createOrderDto.setSupplier(createdSupplier.getId());
        OrderProduct orderProduct = new OrderProduct(5, createdProduct.getId());
        createOrderDto.setProducts(Collections.singletonList(orderProduct));

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.supplierId").value(createdSupplier.getId().toString()));
    }

    @Test
    void getSupplierOrder_whenOrderExists_returnsOrder() throws Exception {
        mockMvc.perform(get("/api/v1/orders/" + createdOrder.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdOrder.getId().toString()));
    }

    @Test
    void getSupplierOrder_whenOrderDoesNotExist_returnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/orders/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getSupplierOrders_returnsListOfOrders() throws Exception {
        mockMvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void updateSupplierOrder_whenOrderExists_updatesAndReturnsOrder() throws Exception {
        SupplierOrderDto updatedOrder = new SupplierOrderDto();
        updatedOrder.setStatus("UPDATED");
        updatedOrder.setProducts(null);
        updatedOrder.setStockMovements(null);

        mockMvc.perform(put("/api/v1/orders/" + createdOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdOrder.getId().toString()))
                .andExpect(jsonPath("$.status").value("UPDATED"));
    }

    @Test
    void updateSupplierOrder_whenOrderDoesNotExist_returnsNotFound() throws Exception {
        SupplierOrderDto orderDto = new SupplierOrderDto();
        orderDto.setStatus("NON_EXISTENT");

        mockMvc.perform(put("/api/v1/orders/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSupplierOrder_whenOrderExists_deletesOrder() throws Exception {
        mockMvc.perform(delete("/api/v1/orders/" + createdOrder.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/orders/" + createdOrder.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSupplierOrder_whenOrderDoesNotExist_returnsOk() throws Exception {
        mockMvc.perform(delete("/api/v1/orders/" + UUID.randomUUID()))
                .andExpect(status().isOk());
    }
}
