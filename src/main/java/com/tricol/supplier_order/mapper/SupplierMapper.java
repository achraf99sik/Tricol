package com.tricol.supplier_order.mapper;

import com.tricol.supplier_order.dto.SupplierDto;
import com.tricol.supplier_order.model.Supplier;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier toEntity(SupplierDto supplierDto);
    SupplierDto toDto(Supplier supplier);
    List<SupplierDto> toDtos(List<Supplier> suppliers);
    List<Supplier> toEntities(List<SupplierDto> supplierDtos);
}
