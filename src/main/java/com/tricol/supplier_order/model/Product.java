package com.tricol.supplier_order.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String description;
    private double unitPrice;
    private String category;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "products")
    private List<SupplierOrder> supplierOrders = new ArrayList<>();
}