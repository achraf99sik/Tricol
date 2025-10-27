package com.tricol.supplier_order.service.interfaces;

import com.tricol.supplier_order.model.Supplier;

import java.util.List;
import java.util.UUID;

public interface SupplierServiceInterface {
    public Supplier getSupplier(UUID supplierId);
    public List<Supplier> getSuppliers(String sort, String sortBy, String searchTerm,String searchBy);
    public void addSupplier(Supplier supplier);
    public void deleteSupplier(UUID supplierId);
    public void updateSupplier(Supplier supplier, UUID supplierId);
}