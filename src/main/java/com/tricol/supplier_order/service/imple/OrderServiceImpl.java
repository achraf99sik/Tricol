package com.tricol.supplier_order.service.imple;

import com.tricol.supplier_order.dto.SupplierOrderDto;
import com.tricol.supplier_order.mapper.SupplierOrderMapper;
import com.tricol.supplier_order.model.SupplierOrder;
import com.tricol.supplier_order.repositroy.OrdersRepositoryInterface;
import com.tricol.supplier_order.service.interfaces.OrderServiceInterface;
import com.tricol.supplier_order.util.PageableUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderServiceInterface {

    private final OrdersRepositoryInterface ordersRepository;
    private final SupplierOrderMapper supplierOrderMapper;

    public OrderServiceImpl(OrdersRepositoryInterface ordersRepository, SupplierOrderMapper supplierOrderMapper) {
        this.ordersRepository = ordersRepository;
        this.supplierOrderMapper = supplierOrderMapper;
    }

    @Override
    public SupplierOrderDto getSupplierOrder() {
        throw new UnsupportedOperationException("The method getSupplierOrder() is not implemented. The interface OrderServiceInterface should be updated to accept a UUID parameter.");
    }

    @Override
    public List<SupplierOrderDto> getSupplierOrders(String sortBy, String order, String searchTerm, String searchBy, Pageable pageable) {
        pageable = PageableUtil.getPageable(sortBy, order, pageable);
        Page<SupplierOrder> page;
        if (searchTerm != null && searchBy != null) {
            switch (searchBy.toLowerCase()) {
                case "status" -> page = this.ordersRepository.findByStatusContainingIgnoreCase(searchTerm, pageable);
                default -> page = this.ordersRepository.findAll(pageable);
            }
        } else {
            page = this.ordersRepository.findAll(pageable);
        }
        return this.supplierOrderMapper.toDtos(page.getContent());
    }

    @Override
    public void deleteSupplierOrder(UUID supplierOrderId) {
        this.ordersRepository.deleteById(supplierOrderId);
    }

    @Override
    public SupplierOrderDto createSupplierOrder(SupplierOrderDto supplierOrderDto) {
        return this.supplierOrderMapper.toDto(this.ordersRepository.save(this.supplierOrderMapper.toEntity(supplierOrderDto)));
    }

    @Override
    public SupplierOrderDto updateSupplierOrder(SupplierOrderDto supplierOrderDto, UUID supplierOrderId) {
        SupplierOrderDto existingOrder = this.supplierOrderMapper.toDto(this.ordersRepository.findById(supplierOrderId).orElseThrow(() -> new RuntimeException("Order not found with id: " + supplierOrderId)));
        Optional.ofNullable(supplierOrderDto.getOrderDate()).ifPresent(existingOrder::setOrderDate);
        Optional.ofNullable(supplierOrderDto.getTotalAmount()).ifPresent(existingOrder::setTotalAmount);
        Optional.ofNullable(supplierOrderDto.getStatus()).ifPresent(existingOrder::setStatus);
        return this.supplierOrderMapper.toDto(this.ordersRepository.save(this.supplierOrderMapper.toEntity(existingOrder)));
    }
}
