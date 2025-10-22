package com.Tricol.controller;

import com.Tricol.model.Supplier;
import com.Tricol.service.interfaces.SupplierServiceInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SuppliersController {
    private SupplierServiceInterface supplierService;

    public void setSupplierService(SupplierServiceInterface supplierService) {
        this.supplierService = supplierService;
    }
    @GetMapping("/{id}")
    public Supplier getSupplier(@PathVariable("id") UUID id) {
        return this.supplierService.getSupplier(id);
    }
    @GetMapping
    public List<Supplier> getSuppliers() {
        return this.supplierService.getSuppliers();
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
