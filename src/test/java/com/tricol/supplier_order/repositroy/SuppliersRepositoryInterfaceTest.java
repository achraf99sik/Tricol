package com.tricol.supplier_order.repositroy;

import com.tricol.supplier_order.model.Supplier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SuppliersRepositoryInterfaceTest {
    @Autowired
    private SuppliersRepositoryInterface repository;

    @Test
    void itShouldFindByCompanyContainingIgnoreCase() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Tricol");
        repository.saveAndFlush(supplier);

        Supplier supplier2 = new Supplier();
        supplier2.setCompany("Alphabet Inc");
        repository.saveAndFlush(supplier2);

        // When
        List<Supplier> suppliers = repository
                .findByCompanyContainingIgnoreCase("tri", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(suppliers),
                () -> assertFalse(suppliers.isEmpty(), "suppliers should not be empty"),
                () -> assertEquals(1, suppliers.size(), "There should be only one supplier"),
                () -> assertEquals("Tricol", suppliers.get(0).getCompany())
        );
    }
    @Test
    void itShouldNotFindByCompanyContainingIgnoreCase() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Tricol");
        repository.saveAndFlush(supplier);

        Supplier supplier2 = new Supplier();
        supplier2.setCompany("Alphabet Inc");
        repository.saveAndFlush(supplier2);

        // When
        List<Supplier> suppliers = repository
                .findByCompanyContainingIgnoreCase("facebook", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(suppliers, "List should not be null"),
                () -> assertTrue(suppliers.isEmpty(), "List should be empty"),
                () -> assertEquals(0, suppliers.size(), "List size should be 0")
        );
    }

    @Test
    void itShouldFindByAddressContainingIgnoreCase() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Tricol");
        supplier.setAddress("123 Main St");
        repository.saveAndFlush(supplier);

        Supplier supplier2 = new Supplier();
        supplier2.setCompany("Alphabet Inc");
        supplier2.setAddress("456 Other St");
        repository.saveAndFlush(supplier2);

        // When
        List<Supplier> suppliers = repository
                .findByAddressContainingIgnoreCase("main", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(suppliers),
                () -> assertFalse(suppliers.isEmpty(), "suppliers should not be empty"),
                () -> assertEquals(1, suppliers.size(), "There should be only one supplier"),
                () -> assertEquals("123 Main St", suppliers.get(0).getAddress())
        );
    }

    @Test
    void itShouldNotFindByAddressContainingIgnoreCase() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Tricol");
        supplier.setAddress("123 Main St");
        repository.saveAndFlush(supplier);

        Supplier supplier2 = new Supplier();
        supplier2.setCompany("Alphabet Inc");
        supplier2.setAddress("456 Other St");
        repository.saveAndFlush(supplier2);

        // When
        List<Supplier> suppliers = repository
                .findByAddressContainingIgnoreCase("broadway", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(suppliers, "List should not be null"),
                () -> assertTrue(suppliers.isEmpty(), "List should be empty"),
                () -> assertEquals(0, suppliers.size(), "List size should be 0")
        );
    }

    @Test
    void itShouldFindByEmailContainingIgnoreCase() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Tricol");
        supplier.setEmail("contact@tricol.com");
        repository.saveAndFlush(supplier);

        Supplier supplier2 = new Supplier();
        supplier2.setCompany("Alphabet Inc");
        supplier2.setEmail("contact@alphabet.com");
        repository.saveAndFlush(supplier2);

        // When
        List<Supplier> suppliers = repository
                .findByEmailContainingIgnoreCase("tricol", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(suppliers),
                () -> assertFalse(suppliers.isEmpty(), "suppliers should not be empty"),
                () -> assertEquals(1, suppliers.size(), "There should be only one supplier"),
                () -> assertEquals("contact@tricol.com", suppliers.get(0).getEmail())
        );
    }

    @Test
    void itShouldNotFindByEmailContainingIgnoreCase() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Tricol");
        supplier.setEmail("contact@tricol.com");
        repository.saveAndFlush(supplier);

        Supplier supplier2 = new Supplier();
        supplier2.setCompany("Alphabet Inc");
        supplier2.setEmail("contact@alphabet.com");
        repository.saveAndFlush(supplier2);

        // When
        List<Supplier> suppliers = repository
                .findByEmailContainingIgnoreCase("microsoft", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(suppliers, "List should not be null"),
                () -> assertTrue(suppliers.isEmpty(), "List should be empty"),
                () -> assertEquals(0, suppliers.size(), "List size should be 0")
        );
    }

    @Test
    void itShouldFindByPhoneContainingIgnoreCase() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Tricol");
        supplier.setPhone("1234567890");
        repository.saveAndFlush(supplier);

        Supplier supplier2 = new Supplier();
        supplier2.setCompany("Alphabet Inc");
        supplier2.setPhone("0987654321");
        repository.saveAndFlush(supplier2);

        // When
        List<Supplier> suppliers = repository
                .findByPhoneContainingIgnoreCase("12345", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(suppliers),
                () -> assertFalse(suppliers.isEmpty(), "suppliers should not be empty"),
                () -> assertEquals(1, suppliers.size(), "There should be only one supplier"),
                () -> assertEquals("1234567890", suppliers.get(0).getPhone())
        );
    }

    @Test
    void itShouldNotFindByPhoneContainingIgnoreCase() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Tricol");
        supplier.setPhone("1234567890");
        repository.saveAndFlush(supplier);

        Supplier supplier2 = new Supplier();
        supplier2.setCompany("Alphabet Inc");
        supplier2.setPhone("0987654321");
        repository.saveAndFlush(supplier2);

        // When
        List<Supplier> suppliers = repository
                .findByPhoneContainingIgnoreCase("11111", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(suppliers, "List should not be null"),
                () -> assertTrue(suppliers.isEmpty(), "List should be empty"),
                () -> assertEquals(0, suppliers.size(), "List size should be 0")
        );
    }

    @Test
    void itShouldFindByCityContainingIgnoreCase() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Tricol");
        supplier.setCity("New York");
        repository.saveAndFlush(supplier);

        Supplier supplier2 = new Supplier();
        supplier2.setCompany("Alphabet Inc");
        supplier2.setCity("Mountain View");
        repository.saveAndFlush(supplier2);

        // When
        List<Supplier> suppliers = repository
                .findByCityContainingIgnoreCase("new", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(suppliers),
                () -> assertFalse(suppliers.isEmpty(), "suppliers should not be empty"),
                () -> assertEquals(1, suppliers.size(), "There should be only one supplier"),
                () -> assertEquals("New York", suppliers.get(0).getCity())
        );
    }

    @Test
    void itShouldNotFindByCityContainingIgnoreCase() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Tricol");
        supplier.setCity("New York");
        repository.saveAndFlush(supplier);

        Supplier supplier2 = new Supplier();
        supplier2.setCompany("Alphabet Inc");
        supplier2.setCity("Mountain View");
        repository.saveAndFlush(supplier2);

        // When
        List<Supplier> suppliers = repository
                .findByCityContainingIgnoreCase("london", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(suppliers, "List should not be null"),
                () -> assertTrue(suppliers.isEmpty(), "List should be empty"),
                () -> assertEquals(0, suppliers.size(), "List size should be 0")
        );
    }

    @Test
    void itShouldFindByIce() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Tricol");
        supplier.setIce(12345);
        repository.saveAndFlush(supplier);

        Supplier supplier2 = new Supplier();
        supplier2.setCompany("Alphabet Inc");
        supplier2.setIce(54321);
        repository.saveAndFlush(supplier2);

        // When
        List<Supplier> suppliers = repository
                .findByIce(12345, PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(suppliers),
                () -> assertFalse(suppliers.isEmpty(), "suppliers should not be empty"),
                () -> assertEquals(1, suppliers.size(), "There should be only one supplier"),
                () -> assertEquals(12345, suppliers.get(0).getIce())
        );
    }

    @Test
    void itShouldNotFindByIce() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Tricol");
        supplier.setIce(12345);
        repository.saveAndFlush(supplier);

        Supplier supplier2 = new Supplier();
        supplier2.setCompany("Alphabet Inc");
        supplier2.setIce(54321);
        repository.saveAndFlush(supplier2);

        // When
        List<Supplier> suppliers = repository
                .findByIce(99999, PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(suppliers, "List should not be null"),
                () -> assertTrue(suppliers.isEmpty(), "List should be empty"),
                () -> assertEquals(0, suppliers.size(), "List size should be 0")
        );
    }

    @Test
    void findByContactContainingIgnoreCase() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Tricol");
        supplier.setContact("John Doe");
        repository.saveAndFlush(supplier);

        Supplier supplier2 = new Supplier();
        supplier2.setCompany("Alphabet Inc");
        supplier2.setContact("Jane Smith");
        repository.saveAndFlush(supplier2);

        // When
        List<Supplier> suppliers = repository
                .findByContactContainingIgnoreCase("john", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(suppliers),
                () -> assertFalse(suppliers.isEmpty(), "suppliers should not be empty"),
                () -> assertEquals(1, suppliers.size(), "There should be only one supplier"),
                () -> assertEquals("John Doe", suppliers.get(0).getContact())
        );
    }

    @Test
    void shouldNotFindByContactContainingIgnoreCase() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setCompany("Tricol");
        supplier.setContact("John Doe");
        repository.saveAndFlush(supplier);

        Supplier supplier2 = new Supplier();
        supplier2.setCompany("Alphabet Inc");
        supplier2.setContact("Jane Smith");
        repository.saveAndFlush(supplier2);

        // When
        List<Supplier> suppliers = repository
                .findByContactContainingIgnoreCase("peter", PageRequest.of(0, 10))
                .getContent();

        // Then
        assertAll(
                () -> assertNotNull(suppliers, "List should not be null"),
                () -> assertTrue(suppliers.isEmpty(), "List should be empty"),
                () -> assertEquals(0, suppliers.size(), "List size should be 0")
        );
    }
}