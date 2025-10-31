package com.tricol.supplier_order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementDto {
    private UUID id;

    private Date date;

    private String quantity;

    private String type;
    private SupplierOrderDto supplierOrder;
    private LocalDateTime createdAt;
}
