package com.tricol.supplier_order.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class PageableUtil {
    public static Pageable getPageable(String sortBy, String order, Pageable pageable) {
        String sortField = (sortBy != null && !sortBy.isBlank()) ? sortBy : "id";
        Sort.Direction direction = Sort.Direction.fromOptionalString(
                Optional.ofNullable(order).orElse("ASC")
        ).orElse(Sort.Direction.ASC);

        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }
        return pageable;
    }
}
