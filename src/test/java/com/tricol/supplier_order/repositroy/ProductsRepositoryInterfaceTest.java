package com.tricol.supplier_order.repositroy;

import com.tricol.supplier_order.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductsRepositoryInterfaceTest {

    @Autowired
    private ProductsRepositoryInterface repository;

    @Test
    void itShouldFindByNameContainingIgnoreCase() {
        // Given
        Product product = new Product();
        product.setName("Laptop");
        repository.saveAndFlush(product);

        Product product2 = new Product();
        product2.setName("Mouse");
        repository.saveAndFlush(product2);

        // When
        List<Product> products = repository
                .findByNameContainingIgnoreCase("lap", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(products),
                () -> assertFalse(products.isEmpty(), "products should not be empty"),
                () -> assertEquals(1, products.size(), "There should be only one product"),
                () -> assertEquals("Laptop", products.get(0).getName())
        );
    }

    @Test
    void itShouldNotFindByNameContainingIgnoreCase() {
        // Given
        Product product = new Product();
        product.setName("Laptop");
        repository.saveAndFlush(product);

        Product product2 = new Product();
        product2.setName("Mouse");
        repository.saveAndFlush(product2);

        // When
        List<Product> products = repository
                .findByNameContainingIgnoreCase("keyboard", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(products, "List should not be null"),
                () -> assertTrue(products.isEmpty(), "List should be empty"),
                () -> assertEquals(0, products.size(), "List size should be 0")
        );
    }

    @Test
    void itShouldFindByDescriptionContainingIgnoreCase() {
        // Given
        Product product = new Product();
        product.setName("Laptop");
        product.setDescription("Powerful computing device");
        repository.saveAndFlush(product);

        Product product2 = new Product();
        product2.setName("Mouse");
        product2.setDescription("Input device");
        repository.saveAndFlush(product2);

        // When
        List<Product> products = repository
                .findByDescriptionContainingIgnoreCase("powerful", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(products),
                () -> assertFalse(products.isEmpty(), "products should not be empty"),
                () -> assertEquals(1, products.size(), "There should be only one product"),
                () -> assertEquals("Powerful computing device", products.get(0).getDescription())
        );
    }

    @Test
    void itShouldNotFindByDescriptionContainingIgnoreCase() {
        // Given
        Product product = new Product();
        product.setName("Laptop");
        product.setDescription("Powerful computing device");
        repository.saveAndFlush(product);

        Product product2 = new Product();
        product2.setName("Mouse");
        product2.setDescription("Input device");
        repository.saveAndFlush(product2);

        // When
        List<Product> products = repository
                .findByDescriptionContainingIgnoreCase("gaming", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(products, "List should not be null"),
                () -> assertTrue(products.isEmpty(), "List should be empty"),
                () -> assertEquals(0, products.size(), "List size should be 0")
        );
    }

    @Test
    void itShouldFindByUnitPriceLessThan() {
        // Given
        Product product = new Product();
        product.setName("Laptop");
        product.setUnitPrice(1200.00);
        repository.saveAndFlush(product);

        Product product2 = new Product();
        product2.setName("Mouse");
        product2.setUnitPrice(25.00);
        repository.saveAndFlush(product2);

        // When
        List<Product> products = repository
                .findByUnitPriceLessThan(100.00, PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(products),
                () -> assertFalse(products.isEmpty(), "products should not be empty"),
                () -> assertEquals(1, products.size(), "There should be only one product"),
                () -> assertTrue(products.get(0).getUnitPrice() < 100.00)
        );
    }

    @Test
    void itShouldNotFindByUnitPriceLessThan() {
        // Given
        Product product = new Product();
        product.setName("Laptop");
        product.setUnitPrice(1200.00);
        repository.saveAndFlush(product);

        Product product2 = new Product();
        product2.setName("Mouse");
        product2.setUnitPrice(25.00);
        repository.saveAndFlush(product2);

        // When
        List<Product> products = repository
                .findByUnitPriceLessThan(10.00, PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(products, "List should not be null"),
                () -> assertTrue(products.isEmpty(), "List should be empty"),
                () -> assertEquals(0, products.size(), "List size should be 0")
        );
    }

    @Test
    void itShouldFindByUnitPriceGreaterThan() {
        // Given
        Product product = new Product();
        product.setName("Laptop");
        product.setUnitPrice(1200.00);
        repository.saveAndFlush(product);

        Product product2 = new Product();
        product2.setName("Mouse");
        product2.setUnitPrice(25.00);
        repository.saveAndFlush(product2);

        // When
        List<Product> products = repository
                .findByUnitPriceGreaterThan(1000.00, PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(products),
                () -> assertFalse(products.isEmpty(), "products should not be empty"),
                () -> assertEquals(1, products.size(), "There should be only one product"),
                () -> assertTrue(products.get(0).getUnitPrice() > 1000.00)
        );
    }

    @Test
    void itShouldNotFindByUnitPriceGreaterThan() {
        // Given
        Product product = new Product();
        product.setName("Laptop");
        product.setUnitPrice(1200.00);
        repository.saveAndFlush(product);

        Product product2 = new Product();
        product2.setName("Mouse");
        product2.setUnitPrice(25.00);
        repository.saveAndFlush(product2);

        // When
        List<Product> products = repository
                .findByUnitPriceGreaterThan(2000.00, PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(products, "List should not be null"),
                () -> assertTrue(products.isEmpty(), "List should be empty"),
                () -> assertEquals(0, products.size(), "List size should be 0")
        );
    }

    @Test
    void itShouldFindByCategoryContainingIgnoreCase() {
        // Given
        Product product = new Product();
        product.setName("Laptop");
        product.setCategory("Electronics");
        repository.saveAndFlush(product);

        Product product2 = new Product();
        product2.setName("Mouse");
        product2.setCategory("Peripherals");
        repository.saveAndFlush(product2);

        // When
        List<Product> products = repository
                .findByCategoryContainingIgnoreCase("elec", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(products),
                () -> assertFalse(products.isEmpty(), "products should not be empty"),
                () -> assertEquals(1, products.size(), "There should be only one product"),
                () -> assertEquals("Electronics", products.get(0).getCategory())
        );
    }

    @Test
    void itShouldNotFindByCategoryContainingIgnoreCase() {
        // Given
        Product product = new Product();
        product.setName("Laptop");
        product.setCategory("Electronics");
        repository.saveAndFlush(product);

        Product product2 = new Product();
        product2.setName("Mouse");
        product2.setCategory("Peripherals");
        repository.saveAndFlush(product2);

        // When
        List<Product> products = repository
                .findByCategoryContainingIgnoreCase("books", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(products, "List should not be null"),
                () -> assertTrue(products.isEmpty(), "List should be empty"),
                () -> assertEquals(0, products.size(), "List size should be 0")
        );
    }
}
