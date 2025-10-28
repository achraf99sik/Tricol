package com.tricol.supplier_order.model;


import com.tricol.supplier_order.enums.MovementType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "stock_movements")
public class StockMovement {

    @Id
    @GeneratedValue
    private UUID id;

    private Date date;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private MovementType type; // ENTREE, SORTIE, AJUSTEMENT

    @ManyToOne
    @JoinColumn(name = "supplier_order_id")
    private SupplierOrder supplierOrder;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
