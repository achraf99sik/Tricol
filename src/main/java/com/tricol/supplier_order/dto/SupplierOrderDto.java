package com.tricol.supplier_order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SupplierOrderDto {
    private UUID id;

    private Date orderDate;

    private String status;

    private String totalAmount;

    private UUID supplierId;

    private List<ProductDto> products = new ArrayList<>();

    private List<StockMovementDto> stockMovements = new ArrayList<>();
    private LocalDateTime createdAt;
}
