package com.tricol.supplier_order.service.imple;

import com.tricol.supplier_order.dto.CreateOrderDto;
import com.tricol.supplier_order.dto.ProductDto;
import com.tricol.supplier_order.dto.SupplierDto;
import com.tricol.supplier_order.dto.SupplierOrderDto;
import com.tricol.supplier_order.mapper.ProductMapper;
import com.tricol.supplier_order.mapper.SupplierMapper;
import com.tricol.supplier_order.mapper.SupplierOrderMapper;
import com.tricol.supplier_order.model.Product;
import com.tricol.supplier_order.model.Supplier;
import com.tricol.supplier_order.model.SupplierOrder;
import com.tricol.supplier_order.repositroy.OrdersRepositoryInterface;
import com.tricol.supplier_order.repositroy.ProductsRepositoryInterface;
import com.tricol.supplier_order.repositroy.SuppliersRepositoryInterface;
import com.tricol.supplier_order.service.interfaces.OrderServiceInterface;
import com.tricol.supplier_order.service.interfaces.SupplierServiceInterface;
import com.tricol.supplier_order.util.PageableUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderServiceInterface {

    private final OrdersRepositoryInterface ordersRepository;
    private final SupplierOrderMapper supplierOrderMapper;
    private final SuppliersRepositoryInterface suppliersRepository;
    private final ProductsRepositoryInterface productsRepository;
    private final SupplierMapper supplierMapper;
    private final ProductMapper productMapper;

    public OrderServiceImpl(
            OrdersRepositoryInterface ordersRepository,
            SupplierOrderMapper supplierOrderMapper,
            SuppliersRepositoryInterface suppliersRepository,
            ProductsRepositoryInterface productsRepository,
            SupplierMapper supplierMapper,
            ProductMapper productMapper
    ) {
        this.ordersRepository = ordersRepository;
        this.supplierOrderMapper = supplierOrderMapper;
        this.suppliersRepository = suppliersRepository;
        this.productsRepository = productsRepository;
        this.supplierMapper = supplierMapper;
        this.productMapper = productMapper;
    }

    @Override
    public SupplierOrderDto getSupplierOrder(UUID supplierOrderId) {
        return this.supplierOrderMapper.toDto(this.ordersRepository.findById(supplierOrderId).orElseThrow(() -> new RuntimeException("Order not found with id: " + supplierOrderId)));
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
    public SupplierOrderDto createSupplierOrder(CreateOrderDto order) {
        List<Product> products = this.productsRepository.findAllById(order.getProducts());
        List<ProductDto> productDtos = this.productMapper.toDtos(products);
        Date orderDate = new Date();
        double totalPrice = productDtos.stream().mapToDouble(p -> Double.parseDouble(p.getUnitPrice())).sum();

        SupplierOrderDto supplierOrder = new SupplierOrderDto()
                .setProducts(productDtos)
                .setSupplierId(order.getSupplier())
                .setOrderDate(orderDate)
                .setTotalAmount(String.valueOf(totalPrice))
                .setStatus("");

        return this.supplierOrderMapper.toDto(this.ordersRepository.save(this.supplierOrderMapper.toEntity(supplierOrder)));
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
