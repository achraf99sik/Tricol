package com.tricol.supplier_order.repositroy;

import com.tricol.supplier_order.enums.MovementType;
import com.tricol.supplier_order.model.StockMovement;
import com.tricol.supplier_order.model.SupplierOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.UUID;

public interface StockMovementRepositoryInterface extends JpaRepository<StockMovement, UUID> {
    Page<StockMovement> findByType(MovementType Type, Pageable pageable);
    Page<StockMovement> findByDateAfter(Date date, Pageable pageable);
    Page<StockMovement> findByDateBefore(Date date, Pageable pageable);
    Page<StockMovement> findByQuantityGreaterThan(int quantity, Pageable pageable);
    Page<StockMovement> findByQuantityLessThan(int quantity, Pageable pageable);
    Page<StockMovement> findBySupplierOrder(SupplierOrder supplierOrder, Pageable pageable);
    Page<StockMovement> findBySupplierOrderId(UUID supplierOrderId, Pageable pageable);
}
