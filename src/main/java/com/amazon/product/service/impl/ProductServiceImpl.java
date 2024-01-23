package com.amazon.product.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazon.product.dto.ProductDto;
import com.amazon.product.dto.ProductResponse;
import com.amazon.product.exceptions.FileNameContainsSpace;
import com.amazon.product.mapper.impl.ProductMapper;
import com.amazon.product.models.Product;
import com.amazon.product.repository.ProductRepo;
import com.amazon.product.service.FileService;
import com.amazon.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepo repo;
    private ProductMapper productMapper;
    private FileService fileService;

    @Value("${project.poster}")
    String path;
    @Value("${base.url}")
    String url;

    public ProductServiceImpl(ProductRepo repo, ProductMapper productMapper, FileService fileService) {
        this.repo = repo;
        this.fileService = fileService;
        this.productMapper = productMapper;
    }

    // public ProductDto createProduct(ProductDto productDto) {
    // Product product=productMapper.mapFrom(productDto);
    // return productMapper.mapTo(repo.save(product));
    // }

    @Override
    public List<ProductDto> findAll() {
        // List<Product> products=repo.findAll();
        List<Product> products = StreamSupport.stream(
                repo.findAll()
                        .spliterator(),
                false)
                .collect(Collectors.toList());

        return products.stream()
                .map(productMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findByName(String name) {
        List<Product> product = repo.findByName(name);
        return product.stream()
                .map(productMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto save(ProductDto product, MultipartFile file) throws IOException {
        // upload the file
        String f = file.getOriginalFilename();
        if (f == null || f.contains(" ")) {
            throw new FileNameContainsSpace();
        }
        String fileName = fileService.uploadFile(path, file);
        // set the value of poster filed to file name
        product.setPoster(fileName);
        // map DTO to OBJECT
        Product pr = productMapper.mapFrom(product);
        // save the product object
        Product pro = repo.save(pr);
        // generate the poster url
        String posterUrl = url + "/api/v1/files/" + fileName;
        product.setPosterurl(posterUrl);
        product.setId(pro.getId());
        return product;
    }

    @Override
    public void deleteProduct(Long id) throws IOException {
        Product product = repo.findById(id).get();
        fileService.deleteResource(path, product.getPoster());
        repo.delete(product);
    }

    @Override
    public ProductResponse getAllTheProducts(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> productPages = repo.findAll(pageable);
        List<Product> products = productPages.getContent();
        List<ProductDto> productDtos = products.stream()
                .map(pr -> {
                    ProductDto p = new ProductDto();
                    p = productMapper.mapTo(pr);
                    String posterUrl = url + "/api/v1/files/" + pr.getPoster();
                    p.setPosterurl(posterUrl);
                    return p;
                })
                .collect(Collectors.toList());
        return new ProductResponse(productDtos, pageNumber, pageSize,
                productPages.getNumberOfElements(),
                productPages.getTotalPages(),
                productPages.isLast());
    }

    @Override
    public ProductResponse getAllTheProductsWithSorting(Integer pageNumber, Integer pageSize, String sortBy,
            String dir) {
        Sort sort = sortBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> productPages = repo.findAll(pageable);
        List<Product> products = productPages.getContent();
        List<ProductDto> productDtos = products.stream()
                .map(pr -> {
                    ProductDto p = new ProductDto();
                    p = productMapper.mapTo(pr);
                    String posterUrl = url + "/api/v1/files/" + pr.getPoster();
                    p.setPosterurl(posterUrl);
                    return p;
                })
                .collect(Collectors.toList());
        return new ProductResponse(productDtos, pageNumber, pageSize,
                productPages.getNumberOfElements(),
                productPages.getTotalPages(),
                productPages.isLast());

    }
}
