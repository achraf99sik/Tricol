package com.tricol.supplier_order.repositroy;

import com.tricol.supplier_order.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public interface ProductsRepositoryInterface extends JpaRepository<Product, UUID> {
    public List<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    public List<Product> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
    public List<Product> findByUnitPriceLessThan(double unitPrice, Pageable pageable);
    public List<Product> findByUnitPriceGreaterThan(double unitPrice, Pageable pageable);
    public List<Product> findByCategoryContainingIgnoreCase(String category, Pageable pageable);
}
