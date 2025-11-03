package com.tricol.supplier_order.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableBuilder {
    public static Pageable buildPageable(String sortBy, String order, Integer page, Integer perPage) {
        int currentPage = (page != null && page > 0) ? page - 1 : 0;
        int pageSize = (perPage != null && perPage > 0) ? perPage : 10;
        Sort sort = SortBuilder.BuildSort(sortBy, order);

        return PageRequest.of(currentPage, pageSize, sort);
    }
}
