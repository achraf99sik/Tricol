package com.tricol.supplier_order.service.imple;

import com.tricol.supplier_order.dto.ProductDto;
import com.tricol.supplier_order.mapper.ProductMapper;
import com.tricol.supplier_order.model.Product;
import com.tricol.supplier_order.repositroy.ProductsRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductsRepositoryInterface productsRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        UUID productId = UUID.randomUUID();
        product = new Product();
        product.setId(productId);
        product.setName("Test Product");

        productDto = new ProductDto();
        productDto.setId(productId);
        productDto.setName("Test Product");
    }

    @Test
    void getProduct_shouldReturnProductDto_whenProductExists() {
        when(productsRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(productDto);

        ProductDto result = productService.getProduct(product.getId());

        assertNotNull(result);
        assertEquals(productDto, result);
        verify(productsRepository).findById(product.getId());
        verify(productMapper).toDto(product);
    }

    @Test
    void getProduct_shouldThrowRuntimeException_whenProductDoesNotExist() {
        when(productsRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.getProduct(UUID.randomUUID()));
        verify(productsRepository).findById(any(UUID.class));
        verify(productMapper, never()).toDto(any(Product.class));
    }

    @Test
    void getProducts_shouldReturnListOfProductDtos() {
        Page<Product> page = new PageImpl<>(Collections.singletonList(product));
        when(productsRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(productMapper.toDtos(Collections.singletonList(product))).thenReturn(Collections.singletonList(productDto));

        List<ProductDto> result = productService.getProducts(null, null, null, null, PageRequest.of(0, 10));

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(productDto, result.get(0));
        verify(productsRepository).findAll(any(Pageable.class));
        verify(productMapper).toDtos(Collections.singletonList(product));
    }

    @Test
    void createProduct_shouldReturnSavedProductDto() {
        when(productMapper.toEntity(productDto)).thenReturn(product);
        when(productsRepository.save(product)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDto);

        ProductDto result = productService.createProduct(productDto);

        assertNotNull(result);
        assertEquals(productDto, result);
        verify(productMapper).toEntity(productDto);
        verify(productsRepository).save(product);
        verify(productMapper).toDto(product);
    }

    @Test
    void deleteProduct_shouldCallDeleteById() {
        UUID productId = UUID.randomUUID();
        doNothing().when(productsRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productsRepository).deleteById(productId);
    }

    @Test
    void updateProduct_shouldReturnUpdatedProductDto_whenProductExists() {
        when(productsRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(productDto);
        when(productsRepository.save(product)).thenReturn(product);

        ProductDto result = productService.updateProduct(productDto, product.getId());

        assertNotNull(result);
        assertEquals(productDto, result);
        verify(productsRepository).findById(product.getId());
        verify(productMapper).toDto(product);
        verify(productsRepository).save(product);
    }

    @Test
    void updateProduct_shouldThrowRuntimeException_whenProductDoesNotExist() {
        when(productsRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.updateProduct(productDto, UUID.randomUUID()));
        verify(productsRepository).findById(any(UUID.class));
        verify(productMapper, never()).toDto(any(Product.class));
        verify(productsRepository, never()).save(any(Product.class));
    }
}