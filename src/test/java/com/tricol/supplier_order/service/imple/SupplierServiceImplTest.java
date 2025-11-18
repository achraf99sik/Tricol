package com.tricol.supplier_order.service.imple;

import com.tricol.supplier_order.dto.SupplierDto;
import com.tricol.supplier_order.mapper.SupplierMapper;
import com.tricol.supplier_order.model.Supplier;
import com.tricol.supplier_order.repositroy.SuppliersRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

@ExtendWith(MockitoExtension.class)
class SupplierServiceImplTest {

    @Mock
    private SuppliersRepositoryInterface suppliersRepository;

    @Mock
    private SupplierMapper supplierMapper;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    private Supplier supplier;
    private SupplierDto supplierDto;

    @BeforeEach
    void setUp() {
        UUID supplierId = UUID.randomUUID();
        supplier = new Supplier();
        supplier.setId(supplierId);
        supplier.setCompany("Test Company");

        supplierDto = new SupplierDto();
        supplierDto.setId(supplierId);
        supplierDto.setCompany("Test Company");
    }

    @Test
    void getSupplier_shouldReturnSupplierDto_whenSupplierExists() {
        when(suppliersRepository.findById(supplier.getId())).thenReturn(Optional.of(supplier));
        when(supplierMapper.toDto(supplier)).thenReturn(supplierDto);

        SupplierDto result = supplierService.getSupplier(supplier.getId());

        assertNotNull(result);
        assertEquals(supplierDto, result);
        verify(suppliersRepository).findById(supplier.getId());
        verify(supplierMapper).toDto(supplier);
    }

    @Test
    void getSupplier_shouldThrowRuntimeException_whenSupplierDoesNotExist() {
        when(suppliersRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> supplierService.getSupplier(UUID.randomUUID()));
        verify(suppliersRepository).findById(any(UUID.class));
        verify(supplierMapper, never()).toDto(any(Supplier.class));
    }

    @Test
    void getSuppliers_shouldReturnListOfSupplierDtos() {
        Page<Supplier> page = new PageImpl<>(Collections.singletonList(supplier));
        when(suppliersRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(supplierMapper.toDtos(Collections.singletonList(supplier))).thenReturn(Collections.singletonList(supplierDto));

        List<SupplierDto> result = supplierService.getSuppliers(null, null, null, null, PageRequest.of(0, 10));

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(supplierDto, result.get(0));
        verify(suppliersRepository).findAll(any(Pageable.class));
        verify(supplierMapper).toDtos(Collections.singletonList(supplier));
    }

    @Test
    void addSupplier_shouldReturnSavedSupplierDto() {
        when(supplierMapper.toEntity(supplierDto)).thenReturn(supplier);
        when(suppliersRepository.save(supplier)).thenReturn(supplier);
        when(supplierMapper.toDto(supplier)).thenReturn(supplierDto);

        SupplierDto result = supplierService.addSupplier(supplierDto);

        assertNotNull(result);
        assertEquals(supplierDto, result);
        verify(supplierMapper).toEntity(supplierDto);
        verify(suppliersRepository).save(supplier);
        verify(supplierMapper).toDto(supplier);
    }

    @Test
    void deleteSupplier_shouldCallDeleteById() {
        UUID supplierId = UUID.randomUUID();
        doNothing().when(suppliersRepository).deleteById(supplierId);

        supplierService.deleteSupplier(supplierId);

        verify(suppliersRepository).deleteById(supplierId);
    }

    @Test
    void updateSupplier_shouldReturnUpdatedSupplierDto_whenSupplierExists() {
        when(suppliersRepository.findById(supplier.getId())).thenReturn(Optional.of(supplier));
        when(supplierMapper.toDto(supplier)).thenReturn(supplierDto);
        when(suppliersRepository.save(supplier)).thenReturn(supplier);

        SupplierDto result = supplierService.updateSupplier(supplierDto, supplier.getId());

        assertNotNull(result);
        assertEquals(supplierDto, result);
        verify(suppliersRepository).findById(supplier.getId());
        verify(supplierMapper).toDto(supplier);
        verify(suppliersRepository).save(supplier);
    }

    @Test
    void updateSupplier_shouldThrowRuntimeException_whenSupplierDoesNotExist() {
        when(suppliersRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> supplierService.updateSupplier(supplierDto, UUID.randomUUID()));
        verify(suppliersRepository).findById(any(UUID.class));
        verify(supplierMapper, never()).toDto(any(Supplier.class));
        verify(suppliersRepository, never()).save(any(Supplier.class));
    }
}