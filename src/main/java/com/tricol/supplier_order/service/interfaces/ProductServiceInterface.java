package com.tricol.supplier_order.service.interfaces;

import com.tricol.supplier_order.dto.ProductDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductServiceInterface {
    ProductDto getProduct(UUID id);
    List<ProductDto> getProducts(String sortBy,
                                 String order,
                                 String searchTerm,
                                 String searchBy,
                                 Pageable pageable);
    void deleteProduct(UUID productId);
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(ProductDto productDto, UUID productId);
}
