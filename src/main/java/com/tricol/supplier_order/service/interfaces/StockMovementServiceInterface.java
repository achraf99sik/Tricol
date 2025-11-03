package com.tricol.supplier_order.service.interfaces;

import com.tricol.supplier_order.dto.StockMovementDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StockMovementServiceInterface {
    List<StockMovementDto> getStockMovements(String sortBy,
                                             String order,
                                             String searchTerm,
                                             String searchBy,
                                             Pageable pageable);
}
