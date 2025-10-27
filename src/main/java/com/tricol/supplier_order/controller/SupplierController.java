package com.tricol.supplier_order.controller;
import com.tricol.supplier_order.model.Supplier;
import com.tricol.supplier_order.service.interfaces.SupplierServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
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
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            @RequestParam(value = "searchBy", required = false) String searchBy
    ) {
        return this.supplierService.getSuppliers(sort, sortBy, searchTerm, searchBy);
    }
    @PostMapping
    public void createSupplier(@RequestBody Supplier supplier) {
        this.supplierService.addSupplier(supplier);
    }
    @PutMapping("/{id}")
    public void updateSupplier(@RequestBody Supplier supplier,@PathVariable("id")  UUID id) {
        this.supplierService.updateSupplier(supplier, id);
    }
    @DeleteMapping("/{id}")
    public void deleteSupplier(@PathVariable("id")  UUID id) {
        this.supplierService.deleteSupplier(id);
    }
}