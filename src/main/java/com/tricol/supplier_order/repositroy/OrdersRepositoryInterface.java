package com.tricol.supplier_order.repositroy;

import com.tricol.supplier_order.model.SupplierOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.UUID;

public interface OrdersRepositoryInterface extends JpaRepository<SupplierOrder, UUID> {
    public Page<SupplierOrder> findByStatusContainingIgnoreCase(String status, Pageable pageable);
    public Page<SupplierOrder> findByOrderDateBefore(Date orderDate, Pageable pageable);
    public Page<SupplierOrder> findByOrderDateAfter(Date orderDate, Pageable pageable);
    public Page<SupplierOrder> findByTotalAmountLessThan(double totalAmount, Pageable pageable);
    public Page<SupplierOrder> findByTotalAmountGreaterThan(double totalAmount, Pageable pageable);
}
