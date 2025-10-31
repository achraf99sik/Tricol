package com.tricol.supplier_order.service.interfaces;

import com.tricol.supplier_order.dto.SupplierDto;
import com.tricol.supplier_order.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface SupplierServiceInterface {
    SupplierDto getSupplier(UUID supplierId);
    List<SupplierDto> getSuppliers(String sortBy, String order, String searchTerm, String searchBy, Pageable pageable);
    SupplierDto addSupplier(SupplierDto supplier);
    void deleteSupplier(UUID supplierId);
    SupplierDto updateSupplier(SupplierDto supplier, UUID supplierId);
}