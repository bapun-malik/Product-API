package com.amazon.product.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.amazon.product.dto.ProductDto;
import com.amazon.product.mapper.Mapper;
import com.amazon.product.models.Product;

@Component
public class ProductMapper implements Mapper<Product,ProductDto> {

    private ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper){
        this.modelMapper=modelMapper;
    }

    @Override
    public Product mapFrom(ProductDto productDto) {
       return modelMapper.map(productDto, Product.class);
    }

    @Override
    public ProductDto mapTo(Product product) {
        return modelMapper.map(product,ProductDto.class);
    }
    
}
