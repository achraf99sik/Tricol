package com.Tricol.service.implementation;

import com.Tricol.repository.implementation.SuppliersRepository;
import com.Tricol.repository.interfaces.SuppliersRepositoryInterface;
import com.Tricol.service.interfaces.SupplierServiceInterface;

public class SupplierService implements SupplierServiceInterface {
    private SuppliersRepositoryInterface suppliersRepository;
    public void setSuppliersRepository(SuppliersRepositoryInterface suppliersRepository) {
        this.suppliersRepository = suppliersRepository;
    }
}
