package com.tricol.supplier_order.service.imple;

import com.tricol.supplier_order.model.Supplier;
import com.tricol.supplier_order.repositroy.SuppliersRepositoryInterface;
import com.tricol.supplier_order.service.interfaces.SupplierServiceInterface;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SupplierSerivceImpl implements SupplierServiceInterface {
    private final SuppliersRepositoryInterface suppliersRepository;
    public SupplierSerivceImpl(SuppliersRepositoryInterface suppliersRepository) {
        this.suppliersRepository = suppliersRepository;
    }
    public Supplier getSupplier(UUID supplierId) {
        return this.suppliersRepository.findById(supplierId).orElseThrow(()->new RuntimeException("Supplier not found: "+ supplierId));
    }
    public List<Supplier> getSuppliers(String sort, String sortBy, String searchTerm, String searchBy) {
        Sort.Direction direction = Sort.Direction.fromOptionalString(sort).orElse(Sort.Direction.ASC);
        String sortField = (sortBy != null && !sortBy.isBlank()) ? sortBy : "id";
        Sort sortObj = Sort.by(direction, sortField);

        if (searchTerm != null && searchBy != null) {
            return switch (searchBy.toLowerCase()) {
                case "company" -> suppliersRepository.findByCompanyContainingIgnoreCase(searchTerm, sortObj);
                case "address" -> suppliersRepository.findByAddressContainingIgnoreCase(searchTerm, sortObj);
                case "email" -> suppliersRepository.findByEmailContainingIgnoreCase(searchTerm, sortObj);
                case "phone" -> suppliersRepository.findByPhoneContainingIgnoreCase(searchTerm, sortObj);
                case "city" -> suppliersRepository.findByCityContainingIgnoreCase(searchTerm, sortObj);
                case "ice" -> suppliersRepository.findByIceContainingIgnoreCase(searchTerm, sortObj);
                case "contact" -> suppliersRepository.findByContactContainingIgnoreCase(searchTerm, sortObj);
                default -> suppliersRepository.findAll(sortObj);
            };
        }
        return suppliersRepository.findAll(sortObj);
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
