package com.tricol.supplier_order.service.interfaces;

import com.tricol.supplier_order.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface SupplierServiceInterface {
    public Supplier getSupplier(UUID supplierId);
    public List<Supplier> getSuppliers(String sortBy, String order, String searchTerm, String searchBy, Pageable pageable);
    public Supplier addSupplier(Supplier supplier);
    public void deleteSupplier(UUID supplierId);
    public Supplier updateSupplier(Supplier supplier, UUID supplierId);
}