package com.tricol.supplier_order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierOrderDto {
    private UUID id;

    private Date orderDate;

    private String status;

    private double totalAmount;

    private SupplierDto supplier;

    private List<ProductDto> products = new ArrayList<>();

    private List<StockMovementDto> stockMovements = new ArrayList<>();
    private LocalDateTime createdAt;
}
