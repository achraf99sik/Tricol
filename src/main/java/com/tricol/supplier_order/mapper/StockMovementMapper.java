package com.tricol.supplier_order.mapper;


import com.tricol.supplier_order.dto.StockMovementDto;
import com.tricol.supplier_order.model.StockMovement;

import java.util.List;

public interface StockMovementMapper {
    StockMovement toEntity(StockMovementDto stockMovementDto);
    StockMovementDto toDto(StockMovement stockMovement);
    List<StockMovementDto> toDtoList(List<StockMovement> stockMovement);
    List<StockMovement> toEntityList(List<StockMovementDto> stockMovementDtos);
}
