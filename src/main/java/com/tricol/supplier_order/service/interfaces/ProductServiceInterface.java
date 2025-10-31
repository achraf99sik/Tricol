package com.tricol.supplier_order.service.interfaces;

import com.tricol.supplier_order.dto.ProductDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductServiceInterface {
    public ProductDto getProduct(UUID id);
    public List<ProductDto> getProducts(String sortBy,
                                        String order,
                                        String searchTerm,
                                        String searchBy,
                                        Pageable pageable);
    public void deleteProduct(UUID productId);
    public ProductDto createProduct(ProductDto productDto);
    public ProductDto updateProduct(ProductDto productDto, UUID productId);
}
