package com.tricol.supplier_order.mapper;

import com.tricol.supplier_order.dto.ProductDto;
import com.tricol.supplier_order.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductDto productDto);
    ProductDto toDto(Product product);
    List<ProductDto> toDtos(List<Product> products);
    List<Product> toEntities(List<ProductDto> productDtos);
}
