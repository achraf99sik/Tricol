package com.tricol.supplier_order.service.imple;

import com.tricol.supplier_order.dto.StockMovementDto;
import com.tricol.supplier_order.mapper.StockMovementMapper;
import com.tricol.supplier_order.model.StockMovement;
import com.tricol.supplier_order.repositroy.StockMovementRepositoryInterface;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class StockMovementServiceImplTest {

    @InjectMocks
    private StockMovementServiceImpl stockMovementServiceImpl;

    @Mock
    private StockMovementRepositoryInterface stockMovementRepository;

    @Mock
    private StockMovementMapper stockMovementMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getStockMovements_shouldReturnListOfStockMovementDto() {
        Page<StockMovement> page = new PageImpl<>(Collections.singletonList(new StockMovement()));
        when(stockMovementRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(stockMovementMapper.toDtos(anyList())).thenReturn(Collections.singletonList(new StockMovementDto()));

        List<StockMovementDto> result = stockMovementServiceImpl.getStockMovements(null, null, null, null, PageRequest.of(0, 10));

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(stockMovementRepository, times(1)).findAll(any(Pageable.class));
        verify(stockMovementMapper, times(1)).toDtos(anyList());
    }
}
