package com.Tricol.service.interfaces;

import com.Tricol.model.Supplier;

import java.util.List;
import java.util.UUID;

public interface SupplierServiceInterface {
    public Supplier getSupplier(UUID supplierId);
    public List<Supplier> getSuppliers();
    public void addSupplier(Supplier supplier);
    public void deleteSupplier(UUID supplierId);
    public void updateSupplier(Supplier supplier, UUID supplierId);
}
