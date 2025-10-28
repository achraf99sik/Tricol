package com.tricol.supplier_order.util;

import org.springframework.data.domain.Sort;

public class SortBuilder {
    public static Sort BuildSort(String sortBy, String order) {
        if (sortBy == null || sortBy.isEmpty()) {
            return Sort.unsorted();
        }

        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(order)) {
            direction = Sort.Direction.DESC;
        }
        return Sort.by(direction, sortBy);
    }
}
