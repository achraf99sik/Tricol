package com.tricol.supplier_order.repositroy;

import com.tricol.supplier_order.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SuppliersRepositoryInterface extends JpaRepository<Supplier, UUID> {
    Page<Supplier> findByCompanyContainingIgnoreCase(String company, Pageable pageable);
    Page<Supplier> findByAddressContainingIgnoreCase(String address, Pageable pageable);
    Page<Supplier> findByEmailContainingIgnoreCase(String email, Pageable pageable);
    Page<Supplier> findByPhoneContainingIgnoreCase(String phone, Pageable pageable);
    Page<Supplier> findByCityContainingIgnoreCase(String city, Pageable pageable);
    Page<Supplier> findByIce(int ice, Pageable pageable);
    Page<Supplier> findByContactContainingIgnoreCase(String contact, Pageable pageable);
}