package com.tricol.supplier_order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDto {
    @NotNull(message = "Supplier is required")
    private UUID supplier;

    @Valid
    @NotEmpty(message = "Products are required")
    private List<OrderProduct> products = new ArrayList<>();
}
