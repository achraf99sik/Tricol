package com.tricol.supplier_order.service.imple;

import com.tricol.supplier_order.dto.SupplierDto;
import com.tricol.supplier_order.mapper.SupplierMapper;
import com.tricol.supplier_order.model.Supplier;
import com.tricol.supplier_order.repositroy.SuppliersRepositoryInterface;
import com.tricol.supplier_order.service.interfaces.SupplierServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final SupplierMapper supplierMapper;
    public SupplierSerivceImpl(SuppliersRepositoryInterface suppliersRepository, SupplierMapper supplierMapper) {
        this.suppliersRepository = suppliersRepository;
        this.supplierMapper = supplierMapper;
    }
    public SupplierDto getSupplier(UUID supplierId) {
        return supplierMapper.toDto(suppliersRepository.findById(supplierId).orElseThrow(()->new RuntimeException("Supplier not found: "+ supplierId)));
    }
    public List<SupplierDto> getSuppliers(
            String sortBy, String order,
            String searchTerm, String searchBy,
            Pageable pageable) {

        String sortField = (sortBy != null && !sortBy.isBlank()) ? sortBy : "id";
        Sort.Direction direction = Sort.Direction.fromOptionalString(
                Optional.ofNullable(order).orElse("ASC")
        ).orElse(Sort.Direction.ASC);

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
                case "ice" -> {
                    try {
                        int ice = Integer.parseInt(searchTerm);
                        page = suppliersRepository.findByIce(ice, pageable);
                    } catch (NumberFormatException e) {
                        page = Page.empty(pageable);
                    }
                }
                case "contact" -> page = suppliersRepository.findByContactContainingIgnoreCase(searchTerm, pageable);
                default -> page = suppliersRepository.findAll(pageable);
            }
        } else {
            page = suppliersRepository.findAll(pageable);
        }

        return supplierMapper.toDtos(page.getContent());
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
