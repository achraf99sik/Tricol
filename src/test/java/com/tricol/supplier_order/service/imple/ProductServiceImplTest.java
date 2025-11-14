package com.tricol.supplier_order.service.imple;

import com.tricol.supplier_order.dto.ProductDto;
import com.tricol.supplier_order.mapper.ProductMapper;
import com.tricol.supplier_order.model.Product;
import com.tricol.supplier_order.repositroy.ProductsRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Mock
    private ProductsRepositoryInterface productsRepository;

    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProduct_shouldReturnProductDto() {
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        when(productsRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toDto(any(Product.class))).thenReturn(new ProductDto());

        ProductDto result = productServiceImpl.getProduct(productId);

        assertNotNull(result);
        verify(productsRepository, times(1)).findById(productId);
        verify(productMapper, times(1)).toDto(any(Product.class));
    }

    @Test
    void getProducts_shouldReturnListOfProductDto() {
        Page<Product> page = new PageImpl<>(Collections.singletonList(new Product()));
        when(productsRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(productMapper.toDtos(anyList())).thenReturn(Collections.singletonList(new ProductDto()));

        List<ProductDto> result = productServiceImpl.getProducts(null, null, null, null, PageRequest.of(0, 10));

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(productsRepository, times(1)).findAll(any(Pageable.class));
        verify(productMapper, times(1)).toDtos(anyList());
    }

    @Test
    void deleteProduct_shouldCallDeleteById() {
        UUID productId = UUID.randomUUID();

        productServiceImpl.deleteProduct(productId);

        verify(productsRepository, times(1)).deleteById(productId);
    }

    @Test
    void createProduct_shouldReturnProductDto() {
        ProductDto productDto = new ProductDto();
        Product product = new Product();
        when(productMapper.toEntity(any(ProductDto.class))).thenReturn(product);
        when(productsRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDto(any(Product.class))).thenReturn(productDto);

        ProductDto result = productServiceImpl.createProduct(productDto);

        assertNotNull(result);
        verify(productMapper, times(1)).toEntity(any(ProductDto.class));
        verify(productsRepository, times(1)).save(any(Product.class));
        verify(productMapper, times(1)).toDto(any(Product.class));
    }

    @Test
    void updateProduct_shouldReturnUpdatedProductDto() {
        UUID productId = UUID.randomUUID();
        ProductDto productDto = new ProductDto();
        Product existingProduct = new Product();
        when(productsRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productMapper.toDto(any(Product.class))).thenReturn(new ProductDto());
        when(productMapper.toEntity(any(ProductDto.class))).thenReturn(existingProduct);
        when(productsRepository.save(any(Product.class))).thenReturn(existingProduct);

        ProductDto result = productServiceImpl.updateProduct(productDto, productId);

        assertNotNull(result);
        verify(productsRepository, times(1)).findById(productId);
        verify(productsRepository, times(1)).save(any(Product.class));
    }
}
