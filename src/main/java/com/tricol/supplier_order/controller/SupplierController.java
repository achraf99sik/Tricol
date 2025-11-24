package com.tricol.supplier_order.controller;
import com.tricol.supplier_order.dto.SupplierDto;
import com.tricol.supplier_order.service.interfaces.SupplierServiceInterface;
import com.tricol.supplier_order.util.PageableBuilder;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/suppliers")
@CrossOrigin(origins = "https://tricol-fontend.vercel.app")
public class SupplierController {
    private final SupplierServiceInterface supplierService;
    private final Bucket bucket;

    public SupplierController(SupplierServiceInterface supplierService) {
        Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
        this.supplierService = supplierService;
    }
    @GetMapping("/{id}")
    public SupplierDto getSupplier(@PathVariable("id") UUID id) {
        return this.supplierService.getSupplier(id);
    }
    @GetMapping
    public List<SupplierDto> getSuppliers(
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            @RequestParam(value = "searchBy", required = false) String searchBy,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "perPage", required = false) Integer perPage
    ) {
        Pageable pageable = PageableBuilder.buildPageable(sortBy, order, page, perPage);
        return this.supplierService.getSuppliers(sortBy,order, searchTerm, searchBy, pageable);
    }
    @PostMapping
    public SupplierDto createSupplier(@Valid @RequestBody SupplierDto supplier) {
        return this.supplierService.addSupplier(supplier);
    }
    @PutMapping("/{id}")
    public SupplierDto updateSupplier(@Valid @RequestBody SupplierDto supplier,@PathVariable("id")  UUID id) {
        return this.supplierService.updateSupplier(supplier, id);
    }
    @DeleteMapping("/{id}")
    public void deleteSupplier(@PathVariable("id")  UUID id) {
        this.supplierService.deleteSupplier(id);
    }
}