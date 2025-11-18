package com.tricol.supplier_order.service.imple;

import com.tricol.supplier_order.dto.CreateOrderDto;
import com.tricol.supplier_order.dto.OrderProduct;
import com.tricol.supplier_order.dto.SupplierOrderDto;
import com.tricol.supplier_order.mapper.ProductMapper;
import com.tricol.supplier_order.mapper.SupplierOrderMapper;
import com.tricol.supplier_order.model.Product;
import com.tricol.supplier_order.model.StockMovement;
import com.tricol.supplier_order.model.SupplierOrder;
import com.tricol.supplier_order.repositroy.OrdersRepositoryInterface;
import com.tricol.supplier_order.repositroy.ProductsRepositoryInterface;
import com.tricol.supplier_order.repositroy.StockMovementRepositoryInterface;
import com.tricol.supplier_order.service.interfaces.StockMovementServiceInterface;
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
class OrderServiceImplTest {

    @Mock
    private OrdersRepositoryInterface ordersRepository;

    @Mock
    private SupplierOrderMapper supplierOrderMapper;

    @Mock
    private ProductsRepositoryInterface productsRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private StockMovementServiceInterface stockMovementService;

    @Mock
    private StockMovementRepositoryInterface stockMovementRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private SupplierOrder supplierOrder;
    private SupplierOrderDto supplierOrderDto;
    private CreateOrderDto createOrderDto;
    private Product product;

    @BeforeEach
    void setUp() {
        UUID orderId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        product.setUnitPrice(10.0);
        product.setQuantity(100);

        supplierOrder = new SupplierOrder();
        supplierOrder.setId(orderId);

        supplierOrderDto = new SupplierOrderDto();
        supplierOrderDto.setId(orderId);

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProductId(productId);
        orderProduct.setQuantity(10);

        createOrderDto = new CreateOrderDto();
        createOrderDto.setSupplier(UUID.randomUUID());
        createOrderDto.setProducts(Collections.singletonList(orderProduct));
    }

    @Test
    void getSupplierOrder_shouldReturnSupplierOrderDto_whenOrderExists() {
        when(ordersRepository.findById(supplierOrder.getId())).thenReturn(Optional.of(supplierOrder));
        when(supplierOrderMapper.toDto(supplierOrder)).thenReturn(supplierOrderDto);

        SupplierOrderDto result = orderService.getSupplierOrder(supplierOrder.getId());

        assertNotNull(result);
        assertEquals(supplierOrderDto, result);
        verify(ordersRepository).findById(supplierOrder.getId());
        verify(supplierOrderMapper).toDto(supplierOrder);
    }

    @Test
    void getSupplierOrder_shouldThrowRuntimeException_whenOrderDoesNotExist() {
        when(ordersRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> orderService.getSupplierOrder(UUID.randomUUID()));
        verify(ordersRepository).findById(any(UUID.class));
        verify(supplierOrderMapper, never()).toDto(any(SupplierOrder.class));
    }

    @Test
    void getSupplierOrders_shouldReturnListOfSupplierOrderDtos() {
        Page<SupplierOrder> page = new PageImpl<>(Collections.singletonList(supplierOrder));
        when(ordersRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(supplierOrderMapper.toDtos(Collections.singletonList(supplierOrder))).thenReturn(Collections.singletonList(supplierOrderDto));

        List<SupplierOrderDto> result = orderService.getSupplierOrders(null, null, null, null, PageRequest.of(0, 10));

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(supplierOrderDto, result.get(0));
        verify(ordersRepository).findAll(any(Pageable.class));
        verify(supplierOrderMapper).toDtos(Collections.singletonList(supplierOrder));
    }

    @Test
    void deleteSupplierOrder_shouldCallDeleteById() {
        UUID orderId = UUID.randomUUID();
        doNothing().when(ordersRepository).deleteById(orderId);

        orderService.deleteSupplierOrder(orderId);

        verify(ordersRepository).deleteById(orderId);
    }

    @Test
    void createSupplierOrder_shouldReturnCreatedSupplierOrderDto() {
        when(productsRepository.findAllById(anyList())).thenReturn(Collections.singletonList(product));
        when(supplierOrderMapper.toEntity(any(SupplierOrderDto.class))).thenReturn(supplierOrder);
        when(ordersRepository.save(any(SupplierOrder.class))).thenReturn(supplierOrder);
        when(ordersRepository.findById(supplierOrder.getId())).thenReturn(Optional.of(supplierOrder));
        when(stockMovementRepository.findBySupplierOrderId(any(UUID.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(new StockMovement())));
        when(supplierOrderMapper.toDto(any(SupplierOrder.class))).thenReturn(supplierOrderDto);

        SupplierOrderDto result = orderService.createSupplierOrder(createOrderDto);

        assertNotNull(result);
        assertEquals(supplierOrderDto, result);
        verify(productsRepository).findAllById(anyList());
        verify(ordersRepository).save(any(SupplierOrder.class));
        verify(stockMovementService).createStockMovements(any(SupplierOrder.class), anyList());
        verify(productsRepository).saveAll(anyList());
        verify(ordersRepository).findById(supplierOrder.getId());
        verify(supplierOrderMapper).toDto(any(SupplierOrder.class));
    }

    @Test
    void updateSupplierOrder_shouldReturnUpdatedSupplierOrderDto_whenOrderExists() {
        when(ordersRepository.findById(supplierOrder.getId())).thenReturn(Optional.of(supplierOrder));
        when(supplierOrderMapper.toDto(supplierOrder)).thenReturn(supplierOrderDto);
        when(ordersRepository.save(supplierOrder)).thenReturn(supplierOrder);

        SupplierOrderDto result = orderService.updateSupplierOrder(supplierOrderDto, supplierOrder.getId());

        assertNotNull(result);
        assertEquals(supplierOrderDto, result);

        verify(ordersRepository).findById(supplierOrder.getId());
        verify(supplierOrderMapper).toDto(supplierOrder);
        verify(ordersRepository).save(supplierOrder);
    }

    @Test
    void updateSupplierOrder_shouldThrowRuntimeException_whenOrderDoesNotExist() {
        when(ordersRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> orderService.updateSupplierOrder(supplierOrderDto, UUID.randomUUID()));
        verify(ordersRepository).findById(any(UUID.class));
        verify(supplierOrderMapper, never()).toDto(any(SupplierOrder.class));
        verify(ordersRepository, never()).save(any(SupplierOrder.class));
    }
}
