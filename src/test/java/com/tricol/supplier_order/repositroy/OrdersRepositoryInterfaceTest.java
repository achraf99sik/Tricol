package com.tricol.supplier_order.repositroy;

import com.tricol.supplier_order.model.SupplierOrder;
import com.tricol.supplier_order.model.Supplier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrdersRepositoryInterfaceTest {

    @Autowired
    private OrdersRepositoryInterface repository;

    @Autowired
    private SuppliersRepositoryInterface supplierRepository; // To save a supplier for the order

    @Test
    void itShouldFindByStatusContainingIgnoreCase() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setStatus("Pending");
        order.setSupplier(supplier);
        repository.saveAndFlush(order);

        SupplierOrder order2 = new SupplierOrder();
        order2.setStatus("Completed");
        order2.setSupplier(supplier);
        repository.saveAndFlush(order2);

        // When
        List<SupplierOrder> orders = repository
                .findByStatusContainingIgnoreCase("pend", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(orders),
                () -> assertFalse(orders.isEmpty(), "orders should not be empty"),
                () -> assertEquals(1, orders.size(), "There should be only one order"),
                () -> assertEquals("Pending", orders.get(0).getStatus())
        );
    }

    @Test
    void itShouldNotFindByStatusContainingIgnoreCase() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setStatus("Pending");
        order.setSupplier(supplier);
        repository.saveAndFlush(order);

        SupplierOrder order2 = new SupplierOrder();
        order2.setStatus("Completed");
        order2.setSupplier(supplier);
        repository.saveAndFlush(order2);

        // When
        List<SupplierOrder> orders = repository
                .findByStatusContainingIgnoreCase("cancelled", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(orders, "List should not be null"),
                () -> assertTrue(orders.isEmpty(), "List should be empty"),
                () -> assertEquals(0, orders.size(), "List size should be 0")
        );
    }

    @Test
    void itShouldFindByOrderDateBefore() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setOrderDate(new Date(System.currentTimeMillis() - 86400000)); // Yesterday
        order.setSupplier(supplier);
        repository.saveAndFlush(order);

        SupplierOrder order2 = new SupplierOrder();
        order2.setOrderDate(new Date(System.currentTimeMillis() + 86400000)); // Tomorrow
        order2.setSupplier(supplier);
        repository.saveAndFlush(order2);

        // When
        List<SupplierOrder> orders = repository
                .findByOrderDateBefore(new Date(), PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(orders),
                () -> assertFalse(orders.isEmpty(), "orders should not be empty"),
                () -> assertEquals(1, orders.size(), "There should be only one order"),
                () -> assertTrue(orders.get(0).getOrderDate().before(new Date()))
        );
    }

    @Test
    void itShouldNotFindByOrderDateBefore() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setOrderDate(new Date(System.currentTimeMillis() + 86400000)); // Tomorrow
        order.setSupplier(supplier);
        repository.saveAndFlush(order);

        // When
        List<SupplierOrder> orders = repository
                .findByOrderDateBefore(new Date(), PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(orders, "List should not be null"),
                () -> assertTrue(orders.isEmpty(), "List should be empty"),
                () -> assertEquals(0, orders.size(), "List size should be 0")
        );
    }

    @Test
    void itShouldFindByOrderDateAfter() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setOrderDate(new Date(System.currentTimeMillis() + 86400000)); // Tomorrow
        order.setSupplier(supplier);
        repository.saveAndFlush(order);

        SupplierOrder order2 = new SupplierOrder();
        order2.setOrderDate(new Date(System.currentTimeMillis() - 86400000)); // Yesterday
        order2.setSupplier(supplier);
        repository.saveAndFlush(order2);

        // When
        List<SupplierOrder> orders = repository
                .findByOrderDateAfter(new Date(), PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(orders),
                () -> assertFalse(orders.isEmpty(), "orders should not be empty"),
                () -> assertEquals(1, orders.size(), "There should be only one order"),
                () -> assertTrue(orders.get(0).getOrderDate().after(new Date()))
        );
    }

    @Test
    void itShouldNotFindByOrderDateAfter() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setOrderDate(new Date(System.currentTimeMillis() - 86400000)); // Yesterday
        order.setSupplier(supplier);
        repository.saveAndFlush(order);

        // When
        List<SupplierOrder> orders = repository
                .findByOrderDateAfter(new Date(), PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(orders, "List should not be null"),
                () -> assertTrue(orders.isEmpty(), "List should be empty"),
                () -> assertEquals(0, orders.size(), "List size should be 0")
        );
    }

    @Test
    void itShouldFindByTotalAmountLessThan() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setTotalAmount(100.0);
        order.setSupplier(supplier);
        repository.saveAndFlush(order);

        SupplierOrder order2 = new SupplierOrder();
        order2.setTotalAmount(200.0);
        order2.setSupplier(supplier);
        repository.saveAndFlush(order2);

        // When
        List<SupplierOrder> orders = repository
                .findByTotalAmountLessThan(150.0, PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(orders),
                () -> assertFalse(orders.isEmpty(), "orders should not be empty"),
                () -> assertEquals(1, orders.size(), "There should be only one order"),
                () -> assertTrue(orders.get(0).getTotalAmount() < 150.0)
        );
    }

    @Test
    void itShouldNotFindByTotalAmountLessThan() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setTotalAmount(200.0);
        order.setSupplier(supplier);
        repository.saveAndFlush(order);

        // When
        List<SupplierOrder> orders = repository
                .findByTotalAmountLessThan(150.0, PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(orders, "List should not be null"),
                () -> assertTrue(orders.isEmpty(), "List should be empty"),
                () -> assertEquals(0, orders.size(), "List size should be 0")
        );
    }

    @Test
    void itShouldFindByTotalAmountGreaterThan() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setTotalAmount(200.0);
        order.setSupplier(supplier);
        repository.saveAndFlush(order);

        SupplierOrder order2 = new SupplierOrder();
        order2.setTotalAmount(100.0);
        order2.setSupplier(supplier);
        repository.saveAndFlush(order2);

        // When
        List<SupplierOrder> orders = repository
                .findByTotalAmountGreaterThan(150.0, PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(orders),
                () -> assertFalse(orders.isEmpty(), "orders should not be empty"),
                () -> assertEquals(1, orders.size(), "There should be only one order"),
                () -> assertTrue(orders.get(0).getTotalAmount() > 150.0)
        );
    }

    @Test
    void itShouldNotFindByTotalAmountGreaterThan() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setTotalAmount(100.0);
        order.setSupplier(supplier);
        repository.saveAndFlush(order);

        // When
        List<SupplierOrder> orders = repository
                .findByTotalAmountGreaterThan(150.0, PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(orders, "List should not be null"),
                () -> assertTrue(orders.isEmpty(), "List should be empty"),
                () -> assertEquals(0, orders.size(), "List size should be 0")
        );
    }
}
