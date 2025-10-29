package com.tricol.supplier_order.service.imple;

import com.tricol.supplier_order.model.Supplier;
import com.tricol.supplier_order.repositroy.SuppliersRepositoryInterface;
import com.tricol.supplier_order.service.interfaces.SupplierServiceInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public List<Supplier> getSuppliers(
            String sortBy, String oder,
            String searchTerm, String searchBy,
            Pageable pageable) {

        String sortField = (sortBy != null && !sortBy.isBlank()) ? sortBy : "id";
        Sort.Direction direction = Optional.ofNullable(oder)
                .map(String::toUpperCase)
                .flatMap(Sort.Direction::fromOptionalString)
                .orElse(Sort.Direction.ASC);

        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        Page<Supplier> page;
        if (searchTerm != null && searchBy != null) {
            switch (searchBy.toLowerCase()) {
                case "company" -> page = suppliersRepository.findByCompanyContainingIgnoreCase(searchTerm, pageable);
                case "address" -> page = suppliersRepository.findByAddressContainingIgnoreCase(searchTerm, pageable);
                case "email" -> page = suppliersRepository.findByEmailContainingIgnoreCase(searchTerm, pageable);
                case "phone" -> page = suppliersRepository.findByPhoneContainingIgnoreCase(searchTerm, pageable);
                case "city" -> page = suppliersRepository.findByCityContainingIgnoreCase(searchTerm, pageable);
                case "ice" -> page = suppliersRepository.findByIceContainingIgnoreCase(searchTerm, pageable);
                case "contact" -> page = suppliersRepository.findByContactContainingIgnoreCase(searchTerm, pageable);
                default -> page = suppliersRepository.findAll(pageable);
            }
        } else {
            page = suppliersRepository.findAll(pageable);
        }

        return page.getContent();
    }


    @Override
    public Supplier addSupplier(Supplier supplier) {
        return this.suppliersRepository.save(supplier);
    }

    @Override
    public void deleteSupplier(UUID supplierId) {
        this.suppliersRepository.deleteById(supplierId);
    }

    @Override
    public Supplier updateSupplier(Supplier supplier, UUID supplierId) {
        Supplier existingSupplier = suppliersRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        existingSupplier.setCompany(supplier.getCompany());
        existingSupplier.setAddress(supplier.getAddress());
        existingSupplier.setEmail(supplier.getEmail());
        existingSupplier.setPhone(supplier.getPhone());
        existingSupplier.setContact(supplier.getContact());
        existingSupplier.setIce(supplier.getIce());

        return suppliersRepository.save(existingSupplier);
    }
}
