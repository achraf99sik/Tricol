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
public class SupplierDto {
    private UUID id;
    private String company;
    private String address;
    private String contact;
    private String email;
    private String phone;
    private String city;
    private String ice;
    private LocalDateTime createdAt;

    private List<SupplierOrderDto> orders = new ArrayList<>();
}
