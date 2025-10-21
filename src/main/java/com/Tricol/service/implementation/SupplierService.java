package com.Tricol.service.implementation;

import com.Tricol.model.Supplier;
import com.Tricol.repository.interfaces.SuppliersRepositoryInterface;
import com.Tricol.service.interfaces.SupplierServiceInterface;

import java.util.List;
import java.util.UUID;

public class SupplierService implements SupplierServiceInterface {
    private SuppliersRepositoryInterface suppliersRepository;
    public void setSuppliersRepository(SuppliersRepositoryInterface suppliersRepository) {
        this.suppliersRepository = suppliersRepository;
    }
    public Supplier getSupplier(UUID supplierId) {
        return this.suppliersRepository.getReferenceById(supplierId);
    }
    public List<Supplier> getSuppliers() {
        return this.suppliersRepository.findAll();
    }

    @Override
    public void addSupplier(Supplier supplier) {
        this.suppliersRepository.save(supplier);
    }

    @Override
    public void deleteSupplier(UUID supplierId) {
        this.suppliersRepository.deleteById(supplierId);
    }

    @Override
    public void updateSupplier(Supplier supplier, UUID supplierId) {
        Supplier existingSupplier = suppliersRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        existingSupplier.setCompany(supplier.getCompany());
        existingSupplier.setAddress(supplier.getAddress());
        existingSupplier.setEmail(supplier.getEmail());
        existingSupplier.setPhone(supplier.getPhone());
        existingSupplier.setContact(supplier.getContact());
        existingSupplier.setIce(supplier.getIce());

        suppliersRepository.save(existingSupplier);
    }
}
