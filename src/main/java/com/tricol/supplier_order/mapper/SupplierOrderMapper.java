package com.tricol.supplier_order.mapper;

import com.tricol.supplier_order.dto.SupplierOrderDto;
import com.tricol.supplier_order.model.SupplierOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierOrderMapper {
    @Mapping(source = "supplierId", target = "supplier.id")
    SupplierOrder toEntity(SupplierOrderDto dto);

    @Mapping(source = "supplier.id", target = "supplierId")
    SupplierOrderDto toDto(SupplierOrder entity);

    List<SupplierOrderDto> toDtos(List<SupplierOrder> supplierOrders);
    List<SupplierOrder> toEntityList(List<SupplierOrderDto> supplierOrderDtos);
}
