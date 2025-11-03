package com.tricol.supplier_order.service.interfaces;

import com.tricol.supplier_order.dto.SupplierOrderDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface OrderServiceInterface {
    SupplierOrderDto getSupplierOrder();
    List<SupplierOrderDto> getSupplierOrders(String sortBy,
                                             String order,
                                             String searchTerm,
                                             String searchBy,
                                             Pageable pageable);
    void deleteSupplierOrder(UUID supplierOrderId);
    SupplierOrderDto createSupplierOrder(SupplierOrderDto supplierOrderDto);
    SupplierOrderDto updateSupplierOrder(SupplierOrderDto supplierOrderDto, UUID supplierOrderId);
}