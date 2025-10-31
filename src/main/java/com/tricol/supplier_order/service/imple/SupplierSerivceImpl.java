package com.tricol.supplier_order.service.imple;

import com.tricol.supplier_order.dto.SupplierDto;
import com.tricol.supplier_order.mapper.SupplierMapper;
import com.tricol.supplier_order.model.Supplier;
import com.tricol.supplier_order.repositroy.SuppliersRepositoryInterface;
import com.tricol.supplier_order.service.interfaces.SupplierServiceInterface;
import com.tricol.supplier_order.util.PageableUtil;
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

        pageable = PageableUtil.getPageable(sortBy, order, pageable);

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
    public SupplierDto addSupplier(SupplierDto supplier) {
        return supplierMapper.toDto(suppliersRepository.save(supplierMapper.toEntity(supplier)));
    }

    @Override
    public void deleteSupplier(UUID supplierId) {
        this.suppliersRepository.deleteById(supplierId);
    }

    @Override
    public SupplierDto updateSupplier(SupplierDto supplier, UUID supplierId) {
        SupplierDto existingSupplier = supplierMapper.toDto(suppliersRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found")));

        Optional.ofNullable(supplier.getCompany()).ifPresent(existingSupplier::setCompany);
        Optional.ofNullable(supplier.getAddress()).ifPresent(existingSupplier::setAddress);
        Optional.ofNullable(supplier.getEmail()).ifPresent(existingSupplier::setEmail);
        Optional.ofNullable(supplier.getPhone()).ifPresent(existingSupplier::setPhone);
        Optional.ofNullable(supplier.getContact()).ifPresent(existingSupplier::setContact);
        Optional.ofNullable(supplier.getIce()).ifPresent(existingSupplier::setIce);

        return supplierMapper.toDto(suppliersRepository.save(supplierMapper.toEntity(existingSupplier)));
    }
}
