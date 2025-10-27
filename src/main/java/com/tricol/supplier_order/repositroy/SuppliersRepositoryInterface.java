package com.tricol.supplier_order.repositroy;

import com.tricol.supplier_order.model.Supplier;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SuppliersRepositoryInterface extends JpaRepository<Supplier, UUID> {
    public List<Supplier> findByCompanyContainingIgnoreCase(String company, Sort sortObj);
    public List<Supplier> findByAddressContainingIgnoreCase(String address, Sort sortObj);
    public List<Supplier> findByEmailContainingIgnoreCase(String email, Sort sortObj);
    public List<Supplier> findByPhoneContainingIgnoreCase(String phone, Sort sortObj);
    public List<Supplier> findByCityContainingIgnoreCase(String city, Sort sortObj);
    public List<Supplier> findByIceContainingIgnoreCase(String ice, Sort sortObj);
    public List<Supplier> findByContactContainingIgnoreCase(String contact, Sort sortObj);
}