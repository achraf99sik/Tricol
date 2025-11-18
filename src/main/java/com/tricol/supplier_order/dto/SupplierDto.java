package com.tricol.supplier_order.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Company is required")
    private String company;

    private String address;
    private String contact;

    @Email(message = "Email should be valid")
    private String email;

    private String phone;
    private String city;
    private int ice;
    private LocalDateTime createdAt;

    private List<SupplierOrderDto> orders = new ArrayList<>();
}
