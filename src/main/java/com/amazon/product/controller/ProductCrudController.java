package com.amazon.product.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazon.product.dto.ProductDto;
import com.amazon.product.exceptions.UserNotFoundException;
import com.amazon.product.mapper.ConvertToProductDtoFromJson;
import com.amazon.product.service.FileService;
import com.amazon.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(path="/api/v1")
public class ProductCrudController {

    private ProductService service;
    private ConvertToProductDtoFromJson converter;

    private FileService fileService;
    @Value("${project.poster}")
    private String path;

    public ProductCrudController(ProductService service,FileService fileService,ConvertToProductDtoFromJson convertToProductDto){
        this.service=service;
        this.converter=convertToProductDto;
        this.fileService=fileService;
    }

    // @GetMapping("/api/products")
    // public ResponseEntity<List<Product>> showAllProduct(){
        
    // }

    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestPart MultipartFile file,
                                                    @RequestPart String productDto) throws IOException{
        ObjectMapper obj=new ObjectMapper();
        ProductDto product=obj.readValue(productDto,ProductDto.class);
        return new ResponseEntity<ProductDto>(service.save(product,file), HttpStatus.CREATED);
    }




    // @PostMapping("/api/product/create")
    // public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto){
    //     ProductDto returnedProduct=service.createProduct(productDto);
    //     return new ResponseEntity<ProductDto>(returnedProduct, HttpStatus.CREATED);
    // }
    // @PostMapping("/api/product/create")
    // public String createProduct(@Valid @ModelAttribute ProductDto product){
    //     service.createProduct(product);
    //     return "redirect:/success";
    // }







    @GetMapping("/success")
    public String showSuccess(@PathVariable String success){
        return "index";
    }

    @GetMapping(path="/products")
    public ResponseEntity<List<ProductDto>> provideAllTheProduct(){
        List<ProductDto> products=service.findAll();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @GetMapping(path="/products/{name}")
    public ResponseEntity<List<ProductDto>> giveProductWithProductName(@PathVariable String name) throws UserNotFoundException{
        List<ProductDto> product=service.findByName(name);
        if(!product.isEmpty()){
            return new ResponseEntity<>(product,HttpStatus.OK);
        }else{
            throw new UserNotFoundException();
        }
    }


    // @PostMapping(path="/upload")
    // public ResponseEntity<String> uploadFile(@RequestPart MultipartFile file) throws IOException{
    //     String uploadedFileName=fileService.uploadFile(path, file);
    //     return ResponseEntity.ok("file Uploaded successfully :"+uploadedFileName);
    // }

    @GetMapping(path="/files/{filename}")
    public void serveTheFile(@PathVariable String filename ,HttpServletResponse res) throws IOException{
        InputStream stream=fileService.getResource(path, filename);
        res.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(stream, res.getOutputStream());
    }

    @DeleteMapping(path="/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) throws IOException{
        service.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
