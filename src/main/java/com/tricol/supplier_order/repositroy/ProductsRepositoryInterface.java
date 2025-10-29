package com.tricol.supplier_order.repositroy;

import com.tricol.supplier_order.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductsRepositoryInterface extends JpaRepository<Product, UUID> {
    public Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    public Page<Product> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
    public Page<Product> findByUnitPriceLessThan(double unitPrice, Pageable pageable);
    public Page<Product> findByUnitPriceGreaterThan(double unitPrice, Pageable pageable);
    public Page<Product> findByCategoryContainingIgnoreCase(String category, Pageable pageable);
}
