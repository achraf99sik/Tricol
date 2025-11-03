package com.tricol.supplier_order.controller;

import com.tricol.supplier_order.dto.StockMovementDto;
import com.tricol.supplier_order.service.interfaces.StockMovementServiceInterface;
import com.tricol.supplier_order.util.PageableBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock-movements")
public class StockMovementController {

    private final StockMovementServiceInterface stockMovementService;

    public StockMovementController(StockMovementServiceInterface stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @GetMapping
    public List<StockMovementDto> getStockMovements(
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            @RequestParam(value = "searchBy", required = false) String searchBy,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "perPage", required = false) Integer perPage
    ) {
        Pageable pageable = PageableBuilder.buildPageable(sortBy, order, page, perPage);
        return this.stockMovementService.getStockMovements(sortBy, order, searchTerm, searchBy, pageable);
    }
}
