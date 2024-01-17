package com.amazon.product.mapper.impl;

import org.springframework.stereotype.Component;

import com.amazon.product.dto.ProductDto;
import com.amazon.product.mapper.ConvertToProductDtoFromJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ConvertToProductDtoFromJsonImpl implements ConvertToProductDtoFromJson{

    @Override
    public ProductDto convertToProductDto(String productDto) throws JsonMappingException, JsonProcessingException {
            ObjectMapper obj=new ObjectMapper();
            return obj.readValue(productDto,ProductDto.class);
    }
    
}
