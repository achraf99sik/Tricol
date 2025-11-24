package com.tricol.supplier_order.controller;

import com.tricol.supplier_order.dto.StockMovementDto;
import com.tricol.supplier_order.service.interfaces.StockMovementServiceInterface;
import com.tricol.supplier_order.util.PageableBuilder;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stock-movements")
@CrossOrigin(origins = "https://tricol-fontend.vercel.app")
public class StockMovementController {

    private final StockMovementServiceInterface stockMovementService;
    private final Bucket bucket;

    public StockMovementController(StockMovementServiceInterface stockMovementService) {
        Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
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
