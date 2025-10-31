package com.tricol.supplier_order.service.interfaces;

import com.tricol.supplier_order.dto.SupplierDto;
import com.tricol.supplier_order.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface SupplierServiceInterface {
    public SupplierDto getSupplier(UUID supplierId);
    public List<SupplierDto> getSuppliers(String sortBy, String order, String searchTerm, String searchBy, Pageable pageable);
    public SupplierDto addSupplier(SupplierDto supplier);
    public void deleteSupplier(UUID supplierId);
    public SupplierDto updateSupplier(SupplierDto supplier, UUID supplierId);
}