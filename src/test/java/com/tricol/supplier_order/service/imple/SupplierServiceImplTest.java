package com.tricol.supplier_order.service.imple;

import com.tricol.supplier_order.dto.SupplierDto;
import com.tricol.supplier_order.mapper.SupplierMapper;
import com.tricol.supplier_order.model.Supplier;
import com.tricol.supplier_order.repositroy.SuppliersRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SupplierServiceImplTest {

    @InjectMocks
    private SupplierServiceImpl supplierServiceImpl;

    @Mock
    private SuppliersRepositoryInterface suppliersRepository;

    @Mock
    private SupplierMapper supplierMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getSupplier_shouldReturnSupplierDto() {
        UUID supplierId = UUID.randomUUID();
        Supplier supplier = new Supplier();
        when(suppliersRepository.findById(supplierId)).thenReturn(Optional.of(supplier));
        when(supplierMapper.toDto(any(Supplier.class))).thenReturn(new SupplierDto());

        SupplierDto result = supplierServiceImpl.getSupplier(supplierId);

        assertNotNull(result);
        verify(suppliersRepository, times(1)).findById(supplierId);
        verify(supplierMapper, times(1)).toDto(any(Supplier.class));
    }

    @Test
    void getSuppliers_shouldReturnListOfSupplierDto() {
        Page<Supplier> page = new PageImpl<>(Collections.singletonList(new Supplier()));
        when(suppliersRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(supplierMapper.toDtos(anyList())).thenReturn(Collections.singletonList(new SupplierDto()));

        List<SupplierDto> result = supplierServiceImpl.getSuppliers(null, null, null, null, PageRequest.of(0, 10));

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(suppliersRepository, times(1)).findAll(any(Pageable.class));
        verify(supplierMapper, times(1)).toDtos(anyList());
    }

    @Test
    void addSupplier_shouldReturnSupplierDto() {
        SupplierDto supplierDto = new SupplierDto();
        Supplier supplier = new Supplier();
        when(supplierMapper.toEntity(any(SupplierDto.class))).thenReturn(supplier);
        when(suppliersRepository.save(any(Supplier.class))).thenReturn(supplier);
        when(supplierMapper.toDto(any(Supplier.class))).thenReturn(supplierDto);

        SupplierDto result = supplierServiceImpl.addSupplier(supplierDto);

        assertNotNull(result);
        verify(supplierMapper, times(1)).toEntity(any(SupplierDto.class));
        verify(suppliersRepository, times(1)).save(any(Supplier.class));
        verify(supplierMapper, times(1)).toDto(any(Supplier.class));
    }

    @Test
    void deleteSupplier_shouldCallDeleteById() {
        UUID supplierId = UUID.randomUUID();

        supplierServiceImpl.deleteSupplier(supplierId);

        verify(suppliersRepository, times(1)).deleteById(supplierId);
    }

    @Test
    void updateSupplier_shouldReturnUpdatedSupplierDto() {
        UUID supplierId = UUID.randomUUID();
        SupplierDto supplierDto = new SupplierDto();
        Supplier existingSupplier = new Supplier();
        when(suppliersRepository.findById(supplierId)).thenReturn(Optional.of(existingSupplier));
        when(supplierMapper.toDto(any(Supplier.class))).thenReturn(new SupplierDto());
        when(supplierMapper.toEntity(any(SupplierDto.class))).thenReturn(existingSupplier);
        when(suppliersRepository.save(any(Supplier.class))).thenReturn(existingSupplier);

        SupplierDto result = supplierServiceImpl.updateSupplier(supplierDto, supplierId);

        assertNotNull(result);
        verify(suppliersRepository, times(1)).findById(supplierId);
        verify(suppliersRepository, times(1)).save(any(Supplier.class));
    }
}
