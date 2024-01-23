package com.amazon.product.dto;

import java.util.List;

public record ProductResponse(List<ProductDto> products,
        Integer pageNumber,
        Integer pageSize,
        int totalElements,
        int totalPages,
        boolean isLast) {
}
