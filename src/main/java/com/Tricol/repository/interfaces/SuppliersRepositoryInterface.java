package com.Tricol.repository.interfaces;

import com.Tricol.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SuppliersRepositoryInterface extends JpaRepository<Supplier, UUID> {
}
