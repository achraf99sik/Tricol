package com.tricol.supplier_order.service.imple;

import com.tricol.supplier_order.dto.OrderProduct;
import com.tricol.supplier_order.dto.StockMovementDto;
import com.tricol.supplier_order.enums.MovementType;
import com.tricol.supplier_order.mapper.StockMovementMapper;
import com.tricol.supplier_order.model.StockMovement;
import com.tricol.supplier_order.model.SupplierOrder;
import com.tricol.supplier_order.repositroy.StockMovementRepositoryInterface;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockMovementServiceImplTest {

    @Mock
    private StockMovementRepositoryInterface stockMovementRepository;

    @Mock
    private StockMovementMapper stockMovementMapper;

    @InjectMocks
    private StockMovementServiceImpl stockMovementService;

    private StockMovement stockMovement;
    private StockMovementDto stockMovementDto;
    private SupplierOrder supplierOrder;
    private OrderProduct orderProduct;

    @BeforeEach
    void setUp() {
        UUID stockMovementId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        stockMovement = new StockMovement();
        stockMovement.setId(stockMovementId);
        stockMovement.setType(MovementType.ENTREE);

        stockMovementDto = new StockMovementDto();
        stockMovementDto.setId(stockMovementId);
        stockMovementDto.setType(MovementType.ENTREE.toString());

        supplierOrder = new SupplierOrder();
        supplierOrder.setId(orderId);

        orderProduct = new OrderProduct();
        orderProduct.setProductId(UUID.randomUUID());
        orderProduct.setQuantity(10);
    }

    @Test
    void getStockMovements_shouldReturnListOfStockMovementDtos() {
        Page<StockMovement> page = new PageImpl<>(Collections.singletonList(stockMovement));
        when(stockMovementRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(stockMovementMapper.toDtos(Collections.singletonList(stockMovement))).thenReturn(Collections.singletonList(stockMovementDto));

        List<StockMovementDto> result = stockMovementService.getStockMovements(null, null, null, null, PageRequest.of(0, 10));

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(stockMovementDto, result.get(0));
        verify(stockMovementRepository).findAll(any(Pageable.class));
        verify(stockMovementMapper).toDtos(Collections.singletonList(stockMovement));
    }

    @Test
    void createStockMovements_shouldSaveStockMovements() {
        List<OrderProduct> orderProducts = Collections.singletonList(orderProduct);
        List<StockMovementDto> stockMovementDtos = Collections.singletonList(stockMovementDto);
        List<StockMovement> stockMovements = Collections.singletonList(stockMovement);

        when(stockMovementMapper.toEntityList(anyList())).thenReturn(stockMovements);

        stockMovementService.createStockMovements(supplierOrder, orderProducts);

        verify(stockMovementRepository).saveAll(stockMovements);
    }
}