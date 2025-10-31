package com.tricol.supplier_order.service.interfaces;

import com.tricol.supplier_order.dto.ProductDto;
import com.tricol.supplier_order.dto.StockMovementDto;

import java.util.List;
import java.util.UUID;

public interface StockMovementServiceInterface {
    List<StockMovementDto> getStockMovements();
}
