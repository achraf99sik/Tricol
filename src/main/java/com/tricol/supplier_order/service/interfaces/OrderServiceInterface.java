package com.tricol.supplier_order.service.interfaces;

import com.tricol.supplier_order.dto.SupplierOrderDto;

import java.util.List;
import java.util.UUID;

public interface OrderServiceInterface {
    public SupplierOrderDto getSupplierOrder();
    public List<SupplierOrderDto> getSupplierOrders();
    public void deleteSupplierOrder(UUID supplierOrderId);
    public SupplierOrderDto createSupplierOrder(SupplierOrderDto supplierOrderDto);
    public SupplierOrderDto updateSupplierOrder(SupplierOrderDto supplierOrderDto, UUID supplierOrderId);
}