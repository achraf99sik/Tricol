package com.tricol.supplier_order.controller;

import com.tricol.supplier_order.dto.CreateOrderDto;
import com.tricol.supplier_order.dto.SupplierOrderDto;
import com.tricol.supplier_order.service.interfaces.OrderServiceInterface;
import com.tricol.supplier_order.util.PageableBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderServiceInterface orderService;

    public OrderController(OrderServiceInterface orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public SupplierOrderDto getSupplierOrder(@PathVariable("id") UUID id) {
        return this.orderService.getSupplierOrder(id);
    }

    @GetMapping
    public List<SupplierOrderDto> getSupplierOrders(
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            @RequestParam(value = "searchBy", required = false) String searchBy,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "perPage", required = false) Integer perPage
    ) {
        Pageable pageable = PageableBuilder.buildPageable(sortBy, order, page, perPage);
        return this.orderService.getSupplierOrders(sortBy, order, searchTerm, searchBy, pageable);
    }

    @PostMapping
    public SupplierOrderDto createSupplierOrder(@RequestBody CreateOrderDto order) {
        return this.orderService.createSupplierOrder(order);
    }

    @PutMapping("/{id}")
    public SupplierOrderDto updateSupplierOrder(@RequestBody SupplierOrderDto supplierOrder, @PathVariable("id") UUID id) {
        return this.orderService.updateSupplierOrder(supplierOrder, id);
    }

    @DeleteMapping("/{id}")
    public void deleteSupplierOrder(@PathVariable("id") UUID id) {
        this.orderService.deleteSupplierOrder(id);
    }
}
