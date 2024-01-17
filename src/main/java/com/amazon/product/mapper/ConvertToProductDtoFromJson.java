package com.amazon.product.mapper;

import com.amazon.product.dto.ProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ConvertToProductDtoFromJson {
    public ProductDto convertToProductDto(String productDto) throws JsonMappingException, JsonProcessingException;
}
