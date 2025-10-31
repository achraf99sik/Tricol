package com.tricol.supplier_order.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "suppliers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {
    @Id
    @GeneratedValue
    private UUID id;
    private String company;
    private String address;
    private String contact;
    private String email;
    private String phone;
    private String city;
    private int ice;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<SupplierOrder> orders = new ArrayList<>();

}