package com.tricol.supplier_order.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "supplier_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierOrder {

    @Id
    @GeneratedValue
    private UUID id;

    private Date orderDate;

    private String status;

    private double totalAmount;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "supplierOrder", cascade = CascadeType.ALL)
    private List<StockMovement> stockMovements = new ArrayList<>();
    @CreationTimestamp
    private LocalDateTime createdAt;
}