package com.tricol.supplier_order.repositroy;

import com.tricol.supplier_order.enums.MovementType;
import com.tricol.supplier_order.model.StockMovement;
import com.tricol.supplier_order.model.SupplierOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface StockMovementRepositoryInterface extends JpaRepository<StockMovement, UUID> {
    public List<StockMovement> findByType(MovementType Type, Pageable pageable);
    public List<StockMovement> findByDateAfter(Date date, Pageable pageable);
    public List<StockMovement> findByDateBefore(Date date, Pageable pageable);
    public List<StockMovement> findByQuantityGreaterThan(int quantity, Pageable pageable);
    public List<StockMovement> findByQuantityLessThan(int quantity, Pageable pageable);
    public List<StockMovement> findBySupplierOrder(SupplierOrder supplierOrder, Pageable pageable);
}
