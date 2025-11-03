package com.tricol.supplier_order.mapper;


import com.tricol.supplier_order.dto.StockMovementDto;
import com.tricol.supplier_order.model.StockMovement;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StockMovementMapper {
    StockMovement toEntity(StockMovementDto stockMovementDto);
    StockMovementDto toDto(StockMovement stockMovement);
    List<StockMovementDto> toDtos(List<StockMovement> stockMovement);
    List<StockMovement> toEntityList(List<StockMovementDto> stockMovementDtos);
}
