package com.amazon.product.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.amazon.product.dto.ProductDto;
import com.amazon.product.dto.ProductResponse;


public interface ProductService {
    // public ProductDto createProduct(ProductDto productDto);

    public List<ProductDto> findAll();

    public List<ProductDto> findByName(String name);

    public ProductDto save(ProductDto product, MultipartFile file) throws IOException;

    public void deleteProduct(Long id) throws IOException;
    public ProductResponse getAllTheProducts(Integer pageNumber,Integer pageSize);
    public ProductResponse getAllTheProductsWithSorting(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
}
