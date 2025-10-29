package com.tricol.supplier_order.repositroy;

import com.tricol.supplier_order.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SuppliersRepositoryInterface extends JpaRepository<Supplier, UUID> {
    public Page<Supplier> findByCompanyContainingIgnoreCase(String company, Pageable pageable);
    public Page<Supplier> findByAddressContainingIgnoreCase(String address, Pageable pageable);
    public Page<Supplier> findByEmailContainingIgnoreCase(String email, Pageable pageable);
    public Page<Supplier> findByPhoneContainingIgnoreCase(String phone, Pageable pageable);
    public Page<Supplier> findByCityContainingIgnoreCase(String city, Pageable pageable);
    public Page<Supplier> findByIceContainingIgnoreCase(String ice, Pageable pageable);
    public Page<Supplier> findByContactContainingIgnoreCase(String contact, Pageable pageable);
}