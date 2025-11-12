package com.tricol.supplier_order.service.imple;

import com.tricol.supplier_order.dto.*;
import com.tricol.supplier_order.mapper.ProductMapper;
import com.tricol.supplier_order.mapper.SupplierOrderMapper;
import com.tricol.supplier_order.model.Product;
import com.tricol.supplier_order.model.StockMovement;
import com.tricol.supplier_order.model.SupplierOrder;
import com.tricol.supplier_order.repositroy.OrdersRepositoryInterface;
import com.tricol.supplier_order.repositroy.ProductsRepositoryInterface;
import com.tricol.supplier_order.repositroy.StockMovementRepositoryInterface;
import com.tricol.supplier_order.service.interfaces.OrderServiceInterface;
import com.tricol.supplier_order.service.interfaces.StockMovementServiceInterface;
import com.tricol.supplier_order.util.PageableUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderServiceInterface {

    private final OrdersRepositoryInterface ordersRepository;
    private final SupplierOrderMapper supplierOrderMapper;
    private final ProductsRepositoryInterface productsRepository;
    private final ProductMapper productMapper;
    private final StockMovementServiceInterface stockMovementService;
    private final StockMovementRepositoryInterface stockMovementRepository;

    public OrderServiceImpl(
            OrdersRepositoryInterface ordersRepository,
            SupplierOrderMapper supplierOrderMapper,
            ProductsRepositoryInterface productsRepository,
            ProductMapper productMapper,
            StockMovementServiceInterface stockMovementService,
            StockMovementRepositoryInterface stockMovementRepository
    ) {
        this.ordersRepository = ordersRepository;
        this.supplierOrderMapper = supplierOrderMapper;
        this.productsRepository = productsRepository;
        this.productMapper = productMapper;
        this.stockMovementService = stockMovementService;
        this.stockMovementRepository = stockMovementRepository;
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
            if (searchBy.toLowerCase().equals("status")) {
                page = this.ordersRepository.findByStatusContainingIgnoreCase(searchTerm, pageable);
            } else {
                page = this.ordersRepository.findAll(pageable);
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
        List<Product> products = fetchProducts(order);
        BigDecimal totalPrice = calculateTotalPrice(order, products);
        SupplierOrder supplierOrder = buildAndSaveSupplierOrder(order, products, totalPrice);

        stockMovementService.createStockMovements(supplierOrder, order.getProducts());
        updateProductQuantities(products, order);

        supplierOrder = reloadSupplierOrderWithMovements(supplierOrder.getId());

        return supplierOrderMapper.toDto(supplierOrder);
    }
    private List<Product> fetchProducts(CreateOrderDto order) {
        List<UUID> productIds = order.getProducts().stream()
                .map(OrderProduct::getProductId)
                .toList();

        List<Product> products = productsRepository.findAllById(productIds);
        if (products.isEmpty()) {
            throw new IllegalArgumentException("No valid products found for IDs: " + productIds);
        }

        return products;
    }

    private BigDecimal calculateTotalPrice(CreateOrderDto order, List<Product> products) {
        return order.getProducts().stream()
                .map(op -> {
                    Product product = findProductById(products, op.getProductId());
                    return new BigDecimal(product.getUnitPrice())
                            .multiply(BigDecimal.valueOf(op.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private SupplierOrder buildAndSaveSupplierOrder(CreateOrderDto order, List<Product> products, BigDecimal totalPrice) {
        SupplierOrder supplierOrder = supplierOrderMapper.toEntity(
                new SupplierOrderDto()
                        .setProducts(productMapper.toDtos(products))
                        .setSupplierId(order.getSupplier())
                        .setOrderDate(new Date())
                        .setTotalAmount(totalPrice.toString())
                        .setStatus("PENDING")
        );
        return ordersRepository.save(supplierOrder);
    }

    private void updateProductQuantities(List<Product> products, CreateOrderDto order) {
        for (OrderProduct op : order.getProducts()) {
            Product product = findProductById(products, op.getProductId());
            int newQuantity = product.getQuantity() - op.getQuantity();
            product.setQuantity(newQuantity);
        }
        productsRepository.saveAll(products);
    }

    private SupplierOrder reloadSupplierOrderWithMovements(UUID orderId) {
        SupplierOrder supplierOrder = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found after save"));

        List<StockMovement> stockMovements = stockMovementRepository
                .findBySupplierOrderId(orderId, PageRequest.of(0, 10))
                .getContent();

        supplierOrder.setStockMovements(stockMovements);
        return supplierOrder;
    }

    private Product findProductById(List<Product> products, UUID productId) {
        return products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));
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
