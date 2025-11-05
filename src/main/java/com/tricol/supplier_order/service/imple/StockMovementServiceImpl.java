package com.tricol.supplier_order.service.imple;

import com.tricol.supplier_order.dto.OrderProduct;
import com.tricol.supplier_order.dto.StockMovementDto;
import com.tricol.supplier_order.enums.MovementType;
import com.tricol.supplier_order.mapper.StockMovementMapper;
import com.tricol.supplier_order.model.StockMovement;
import com.tricol.supplier_order.model.SupplierOrder;
import com.tricol.supplier_order.repositroy.StockMovementRepositoryInterface;
import com.tricol.supplier_order.service.interfaces.StockMovementServiceInterface;
import com.tricol.supplier_order.util.PageableUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Service
public class StockMovementServiceImpl implements StockMovementServiceInterface {

    private final StockMovementRepositoryInterface stockMovementRepository;
    private final StockMovementMapper stockMovementMapper;

    public StockMovementServiceImpl(StockMovementRepositoryInterface stockMovementRepository, StockMovementMapper stockMovementMapper) {
        this.stockMovementRepository = stockMovementRepository;
        this.stockMovementMapper = stockMovementMapper;
    }

    @Override
    public List<StockMovementDto> getStockMovements(String sortBy, String order, String searchTerm, String searchBy, Pageable pageable) {
        pageable = PageableUtil.getPageable(sortBy, order, pageable);
        Page<StockMovement> page;
        if (searchTerm != null && searchBy != null) {
            switch (searchBy.toLowerCase()) {
                case "type" -> page = this.stockMovementRepository.findByType(MovementType.valueOf(searchTerm.toUpperCase()), pageable);
                case "date_after" -> page = this.stockMovementRepository.findByDateAfter(new Date(Long.parseLong(searchTerm)), pageable);
                case "date_before" -> page = this.stockMovementRepository.findByDateBefore(new Date(Long.parseLong(searchTerm)), pageable);
                case "quantity_greater" -> page = this.stockMovementRepository.findByQuantityGreaterThan(Integer.parseInt(searchTerm), pageable);
                case "quantity_less" -> page = this.stockMovementRepository.findByQuantityLessThan(Integer.parseInt(searchTerm), pageable);
                default -> page = this.stockMovementRepository.findAll(pageable);
            }
        } else {
            page = this.stockMovementRepository.findAll(pageable);
        }
        return this.stockMovementMapper.toDtos(page.getContent());
    }
    public void createStockMovements(SupplierOrder supplierOrder, List<OrderProduct> orders) {
        List<StockMovementDto> stockMovements = new ArrayList<>();
        orders.forEach(op -> {
            StockMovementDto movementDto = new StockMovementDto().setSupplierOrderId(supplierOrder.getId()).setDate(new Date()).setQuantity(String.valueOf(op.getQuantity())).setType(MovementType.SORTIE.toString());
            stockMovements.add(movementDto);
        });
        this.stockMovementRepository.saveAll(this.stockMovementMapper.toEntityList(stockMovements));
    }
}
