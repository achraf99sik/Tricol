package com.tricol.supplier_order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private UUID id;

    private String name;
    private String description;
    private int quantity;
    private double unitPrice;
    private String category;
    private LocalDateTime createdAt;

    private List<SupplierOrderDto> supplierOrders = new ArrayList<>();
}
