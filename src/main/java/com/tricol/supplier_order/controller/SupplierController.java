package com.tricol.supplier_order.controller;
import com.tricol.supplier_order.model.Supplier;
import com.tricol.supplier_order.service.interfaces.SupplierServiceInterface;
import com.tricol.supplier_order.util.SortBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {
    private final SupplierServiceInterface supplierService;

    public SupplierController(SupplierServiceInterface supplierService) {
        this.supplierService = supplierService;
    }
    @GetMapping("/{id}")
    public Supplier getSupplier(@PathVariable("id") UUID id) {
        return this.supplierService.getSupplier(id);
    }
    @GetMapping
    public List<Supplier> getSuppliers(
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            @RequestParam(value = "searchBy", required = false) String searchBy,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "perPage", required = false) Integer perPage
    ) {
        int currentPage = (page != null && page > 0) ? page - 1 : 0;
        int pageSize = (perPage != null && perPage > 0) ? perPage : 10;
        Sort sort = SortBuilder.BuildSort(sortBy, order);

        Pageable pageable = PageRequest.of(currentPage, pageSize, sort);
        return this.supplierService.getSuppliers(sortBy,order, searchTerm, searchBy, pageable);
    }
    @PostMapping
    public Supplier createSupplier(@RequestBody Supplier supplier) {
        return this.supplierService.addSupplier(supplier);
    }
    @PutMapping("/{id}")
    public Supplier updateSupplier(@RequestBody Supplier supplier,@PathVariable("id")  UUID id) {
        return this.supplierService.updateSupplier(supplier, id);
    }
    @DeleteMapping("/{id}")
    public void deleteSupplier(@PathVariable("id")  UUID id) {
        this.supplierService.deleteSupplier(id);
    }
}