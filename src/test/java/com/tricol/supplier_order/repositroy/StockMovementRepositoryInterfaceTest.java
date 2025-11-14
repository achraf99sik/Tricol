package com.tricol.supplier_order.repositroy;

import com.tricol.supplier_order.enums.MovementType;
import com.tricol.supplier_order.model.StockMovement;
import com.tricol.supplier_order.model.Supplier;
import com.tricol.supplier_order.model.SupplierOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StockMovementRepositoryInterfaceTest {

    @Autowired
    private StockMovementRepositoryInterface repository;

    @Autowired
    private SuppliersRepositoryInterface supplierRepository;

    @Autowired
    private OrdersRepositoryInterface orderRepository;

    @Test
    void itShouldFindByType() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setSupplier(supplier);
        orderRepository.saveAndFlush(order);

        StockMovement stockMovement = new StockMovement();
        stockMovement.setType(MovementType.ENTREE);
        stockMovement.setSupplierOrder(order);
        repository.saveAndFlush(stockMovement);

        StockMovement stockMovement2 = new StockMovement();
        stockMovement2.setType(MovementType.SORTIE);
        stockMovement2.setSupplierOrder(order);
        repository.saveAndFlush(stockMovement2);

        // When
        List<StockMovement> stockMovements = repository
                .findByType(MovementType.ENTREE, PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(stockMovements),
                () -> assertFalse(stockMovements.isEmpty(), "stockMovements should not be empty"),
                () -> assertEquals(1, stockMovements.size(), "There should be only one stockMovement"),
                () -> assertEquals(MovementType.ENTREE, stockMovements.get(0).getType())
        );
    }

    @Test
    void itShouldNotFindByType() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setSupplier(supplier);
        orderRepository.saveAndFlush(order);

        StockMovement stockMovement = new StockMovement();
        stockMovement.setType(MovementType.ENTREE);
        stockMovement.setSupplierOrder(order);
        repository.saveAndFlush(stockMovement);

        // When
        List<StockMovement> stockMovements = repository
                .findByType(MovementType.SORTIE, PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(stockMovements, "List should not be null"),
                () -> assertTrue(stockMovements.isEmpty(), "List should be empty"),
                () -> assertEquals(0, stockMovements.size(), "List size should be 0")
        );
    }

    @Test
    void itShouldFindByDateAfter() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setSupplier(supplier);
        orderRepository.saveAndFlush(order);

        StockMovement stockMovement = new StockMovement();
        stockMovement.setDate(new Date(System.currentTimeMillis() + 86400000)); // Tomorrow
        stockMovement.setSupplierOrder(order);
        repository.saveAndFlush(stockMovement);

        StockMovement stockMovement2 = new StockMovement();
        stockMovement2.setDate(new Date(System.currentTimeMillis() - 86400000)); // Yesterday
        stockMovement2.setSupplierOrder(order);
        repository.saveAndFlush(stockMovement2);

        // When
        List<StockMovement> stockMovements = repository
                .findByDateAfter(new Date(), PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(stockMovements),
                () -> assertFalse(stockMovements.isEmpty(), "stockMovements should not be empty"),
                () -> assertEquals(1, stockMovements.size(), "There should be only one stockMovement"),
                () -> assertTrue(stockMovements.get(0).getDate().after(new Date()))
        );
    }

    @Test
    void itShouldNotFindByDateAfter() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setSupplier(supplier);
        orderRepository.saveAndFlush(order);

        StockMovement stockMovement = new StockMovement();
        stockMovement.setDate(new Date(System.currentTimeMillis() - 86400000)); // Yesterday
        stockMovement.setSupplierOrder(order);
        repository.saveAndFlush(stockMovement);

        // When
        List<StockMovement> stockMovements = repository
                .findByDateAfter(new Date(), PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(stockMovements, "List should not be null"),
                () -> assertTrue(stockMovements.isEmpty(), "List should be empty"),
                () -> assertEquals(0, stockMovements.size(), "List size should be 0")
        );
    }

    @Test
    void itShouldFindByDateBefore() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setSupplier(supplier);
        orderRepository.saveAndFlush(order);

        StockMovement stockMovement = new StockMovement();
        stockMovement.setDate(new Date(System.currentTimeMillis() - 86400000)); // Yesterday
        stockMovement.setSupplierOrder(order);
        repository.saveAndFlush(stockMovement);

        StockMovement stockMovement2 = new StockMovement();
        stockMovement2.setDate(new Date(System.currentTimeMillis() + 86400000)); // Tomorrow
        stockMovement2.setSupplierOrder(order);
        repository.saveAndFlush(stockMovement2);

        // When
        List<StockMovement> stockMovements = repository
                .findByDateBefore(new Date(), PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(stockMovements),
                () -> assertFalse(stockMovements.isEmpty(), "stockMovements should not be empty"),
                () -> assertEquals(1, stockMovements.size(), "There should be only one stockMovement"),
                () -> assertTrue(stockMovements.get(0).getDate().before(new Date()))
        );
    }

    @Test
    void itShouldNotFindByDateBefore() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setSupplier(supplier);
        orderRepository.saveAndFlush(order);

        StockMovement stockMovement = new StockMovement();
        stockMovement.setDate(new Date(System.currentTimeMillis() + 86400000)); // Tomorrow
        stockMovement.setSupplierOrder(order);
        repository.saveAndFlush(stockMovement);

        // When
        List<StockMovement> stockMovements = repository
                .findByDateBefore(new Date(), PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(stockMovements, "List should not be null"),
                () -> assertTrue(stockMovements.isEmpty(), "List should be empty"),
                () -> assertEquals(0, stockMovements.size(), "List size should be 0")
        );
    }

    @Test
    void itShouldFindByQuantityGreaterThan() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setSupplier(supplier);
        orderRepository.saveAndFlush(order);

        StockMovement stockMovement = new StockMovement();
        stockMovement.setQuantity(10);
        stockMovement.setSupplierOrder(order);
        repository.saveAndFlush(stockMovement);

        StockMovement stockMovement2 = new StockMovement();
        stockMovement2.setQuantity(5);
        stockMovement2.setSupplierOrder(order);
        repository.saveAndFlush(stockMovement2);

        // When
        List<StockMovement> stockMovements = repository
                .findByQuantityGreaterThan(7, PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(stockMovements),
                () -> assertFalse(stockMovements.isEmpty(), "stockMovements should not be empty"),
                () -> assertEquals(1, stockMovements.size(), "There should be only one stockMovement"),
                () -> assertTrue(stockMovements.get(0).getQuantity() > 7)
        );
    }

    @Test
    void itShouldNotFindByQuantityGreaterThan() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setSupplier(supplier);
        orderRepository.saveAndFlush(order);

        StockMovement stockMovement = new StockMovement();
        stockMovement.setQuantity(5);
        stockMovement.setSupplierOrder(order);
        repository.saveAndFlush(stockMovement);

        // When
        List<StockMovement> stockMovements = repository
                .findByQuantityGreaterThan(7, PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(stockMovements, "List should not be null"),
                () -> assertTrue(stockMovements.isEmpty(), "List should be empty"),
                () -> assertEquals(0, stockMovements.size(), "List size should be 0")
        );
    }

    @Test
    void itShouldFindByQuantityLessThan() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setSupplier(supplier);
        orderRepository.saveAndFlush(order);

        StockMovement stockMovement = new StockMovement();
        stockMovement.setQuantity(5);
        stockMovement.setSupplierOrder(order);
        repository.saveAndFlush(stockMovement);

        StockMovement stockMovement2 = new StockMovement();
        stockMovement2.setQuantity(10);
        stockMovement2.setSupplierOrder(order);
        repository.saveAndFlush(stockMovement2);

        // When
        List<StockMovement> stockMovements = repository
                .findByQuantityLessThan(7, PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(stockMovements),
                () -> assertFalse(stockMovements.isEmpty(), "stockMovements should not be empty"),
                () -> assertEquals(1, stockMovements.size(), "There should be only one stockMovement"),
                () -> assertTrue(stockMovements.get(0).getQuantity() < 7)
        );
    }

    @Test
    void itShouldNotFindByQuantityLessThan() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setSupplier(supplier);
        orderRepository.saveAndFlush(order);

        StockMovement stockMovement = new StockMovement();
        stockMovement.setQuantity(10);
        stockMovement.setSupplierOrder(order);
        repository.saveAndFlush(stockMovement);

        // When
        List<StockMovement> stockMovements = repository
                .findByQuantityLessThan(7, PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(stockMovements, "List should not be null"),
                () -> assertTrue(stockMovements.isEmpty(), "List should be empty"),
                () -> assertEquals(0, stockMovements.size(), "List size should be 0")
        );
    }

    @Test
    void itShouldFindBySupplierOrder() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setSupplier(supplier);
        orderRepository.saveAndFlush(order);

        SupplierOrder order2 = new SupplierOrder();
        order2.setSupplier(supplier);
        orderRepository.saveAndFlush(order2);

        StockMovement stockMovement = new StockMovement();
        stockMovement.setSupplierOrder(order);
        repository.saveAndFlush(stockMovement);

        StockMovement stockMovement2 = new StockMovement();
        stockMovement2.setSupplierOrder(order2);
        repository.saveAndFlush(stockMovement2);

        // When
        List<StockMovement> stockMovements = repository
                .findBySupplierOrder(order, PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(stockMovements),
                () -> assertFalse(stockMovements.isEmpty(), "stockMovements should not be empty"),
                () -> assertEquals(1, stockMovements.size(), "There should be only one stockMovement"),
                () -> assertEquals(order.getId(), stockMovements.get(0).getSupplierOrder().getId())
        );
    }

    @Test
    void itShouldNotFindBySupplierOrder() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setSupplier(supplier);
        orderRepository.saveAndFlush(order);

        SupplierOrder order2 = new SupplierOrder();
        order2.setSupplier(supplier);
        orderRepository.saveAndFlush(order2);

        StockMovement stockMovement = new StockMovement();
        stockMovement.setSupplierOrder(order);
        repository.saveAndFlush(stockMovement);

        // When
        List<StockMovement> stockMovements = repository
                .findBySupplierOrder(order2, PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(stockMovements, "List should not be null"),
                () -> assertTrue(stockMovements.isEmpty(), "List should be empty"),
                () -> assertEquals(0, stockMovements.size(), "List size should be 0")
        );
    }

    @Test
    void itShouldFindBySupplierOrderId() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setSupplier(supplier);
        orderRepository.saveAndFlush(order);

        SupplierOrder order2 = new SupplierOrder();
        order2.setSupplier(supplier);
        orderRepository.saveAndFlush(order2);

        StockMovement stockMovement = new StockMovement();
        stockMovement.setSupplierOrder(order);
        repository.saveAndFlush(stockMovement);

        StockMovement stockMovement2 = new StockMovement();
        stockMovement2.setSupplierOrder(order2);
        repository.saveAndFlush(stockMovement2);

        // When
        List<StockMovement> stockMovements = repository
                .findBySupplierOrderId(order.getId(), PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(stockMovements),
                () -> assertFalse(stockMovements.isEmpty(), "stockMovements should not be empty"),
                () -> assertEquals(1, stockMovements.size(), "There should be only one stockMovement"),
                () -> assertEquals(order.getId(), stockMovements.get(0).getSupplierOrder().getId())
        );
    }

    @Test
    void itShouldNotFindBySupplierOrderId() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Test Supplier");
        supplierRepository.saveAndFlush(supplier);

        SupplierOrder order = new SupplierOrder();
        order.setSupplier(supplier);
        orderRepository.saveAndFlush(order);

        SupplierOrder order2 = new SupplierOrder();
        order2.setSupplier(supplier);
        orderRepository.saveAndFlush(order2);

        StockMovement stockMovement = new StockMovement();
        stockMovement.setSupplierOrder(order);
        repository.saveAndFlush(stockMovement);

        // When
        List<StockMovement> stockMovements = repository
                .findBySupplierOrderId(order2.getId(), PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(stockMovements, "List should not be null"),
                () -> assertTrue(stockMovements.isEmpty(), "List should be empty"),
                () -> assertEquals(0, stockMovements.size(), "List size should be 0")
        );
    }
}
