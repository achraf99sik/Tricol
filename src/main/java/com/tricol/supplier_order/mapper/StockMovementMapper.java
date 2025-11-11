package com.tricol.supplier_order.mapper;


import com.tricol.supplier_order.dto.StockMovementDto;
import com.tricol.supplier_order.model.StockMovement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface StockMovementMapper {
    @Mapping(source = "supplierOrderId", target = "supplierOrder.id")
    StockMovement toEntity(StockMovementDto dto);

    @Mapping(source = "supplierOrder.id", target = "supplierOrderId")
    StockMovementDto toDto(StockMovement entity);
    List<StockMovementDto> toDtos(List<StockMovement> stockMovement);
    List<StockMovement> toEntityList(List<StockMovementDto> stockMovementDtos);
}