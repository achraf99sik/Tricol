package com.tricol.supplier_order.mapper;

import com.tricol.supplier_order.dto.SupplierOrderDto;
import com.tricol.supplier_order.model.SupplierOrder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierOrderMapper {
    SupplierOrder toEntity(SupplierOrderDto supplierOrderDto);
    SupplierOrderDto toDto(SupplierOrder supplierOrder);
    List<SupplierOrderDto> toDtos(List<SupplierOrder> supplierOrders);
    List<SupplierOrder> toEntityList(List<SupplierOrderDto> supplierOrderDtos);
}
