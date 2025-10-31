package com.tricol.supplier_order.service.imple;

import com.tricol.supplier_order.dto.ProductDto;
import com.tricol.supplier_order.mapper.ProductMapper;
import com.tricol.supplier_order.model.Product;
import com.tricol.supplier_order.repositroy.ProductsRepositoryInterface;
import com.tricol.supplier_order.service.interfaces.ProductServiceInterface;
import com.tricol.supplier_order.util.PageableUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductServiceImpl implements ProductServiceInterface {
    private final ProductsRepositoryInterface productsRepository;
    private final ProductMapper productMapper;
    public ProductServiceImpl(ProductsRepositoryInterface productsRepository, ProductMapper productMapper) {
        this.productsRepository = productsRepository;
        this.productMapper = productMapper;
    }
    @Override
    public ProductDto getProduct(UUID productId) {
        return this.productMapper.toDto(this.productsRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId)));
    }

    @Override
    public List<ProductDto> getProducts(
            String sortBy,
            String order,
            String searchTerm,
            String searchBy,
            Pageable pageable
    ) {
        pageable = PageableUtil.getPageable(sortBy, order, pageable);
        Page<Product> page;
        if (searchTerm != null && searchBy != null) {
            switch (searchBy.toLowerCase()){
                case "name"-> page = this.productsRepository.findByNameContainingIgnoreCase(searchTerm, pageable);
                case "description"-> page = this.productsRepository.findByDescriptionContainingIgnoreCase(searchTerm, pageable);
                case "price_less" -> page = this.productsRepository.findByUnitPriceLessThan(Double.parseDouble(searchTerm), pageable);
                case "price_greater" -> page = this.productsRepository.findByUnitPriceGreaterThan(Double.parseDouble(searchTerm), pageable);
                case "category" -> page = this.productsRepository.findByCategoryContainingIgnoreCase(searchTerm, pageable);
                default -> page = this.productsRepository.findAll(pageable);
            }
        }else {
            page = this.productsRepository.findAll(pageable);
        }
        return this.productMapper.toDtos(page.getContent());
    }

    @Override
    public void deleteProduct(UUID productId) {
        this.productsRepository.deleteById(productId);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        return this.productMapper.toDto(this.productsRepository.save(productMapper.toEntity(productDto)));
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, UUID productId) {
        ProductDto existingProduct = this.productMapper.toDto(this.productsRepository.findById(productId).orElseThrow(()-> new RuntimeException("Product not found with id: " + productId)));
        Optional.ofNullable(productDto.getName()).ifPresent(existingProduct::setName);
        Optional.ofNullable(productDto.getDescription()).ifPresent(existingProduct::setDescription);
        Optional.ofNullable(productDto.getUnitPrice()).ifPresent(existingProduct::setUnitPrice);
        Optional.ofNullable(productDto.getCategory()).ifPresent(existingProduct::setCategory);
        Optional.ofNullable(productDto.getQuantity()).ifPresent(existingProduct::setQuantity);
        return this.productMapper.toDto(this.productsRepository.save(productMapper.toEntity(existingProduct)));
    }
}
