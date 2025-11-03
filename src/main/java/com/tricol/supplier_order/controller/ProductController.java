package com.tricol.supplier_order.controller;

import com.tricol.supplier_order.dto.ProductDto;
import com.tricol.supplier_order.service.interfaces.ProductServiceInterface;
import com.tricol.supplier_order.util.PageableBuilder;
import com.tricol.supplier_order.util.SortBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductServiceInterface productService;

    public ProductController(ProductServiceInterface productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable("id") UUID id) {
        return this.productService.getProduct(id);
    }

    @GetMapping
    public List<ProductDto> getProducts(
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            @RequestParam(value = "searchBy", required = false) String searchBy,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "perPage", required = false) Integer perPage
    ) {
        Pageable pageable = PageableBuilder.buildPageable(sortBy, order, page, perPage);
        return this.productService.getProducts(sortBy, order, searchTerm, searchBy, pageable);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto product) {
        return this.productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@RequestBody ProductDto product, @PathVariable("id") UUID id) {
        return this.productService.updateProduct(product, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") UUID id) {
        this.productService.deleteProduct(id);
    }
}
