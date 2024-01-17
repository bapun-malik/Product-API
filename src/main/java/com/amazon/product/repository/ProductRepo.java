package com.amazon.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazon.product.models.Product;

public interface ProductRepo extends JpaRepository<Product,Long> {
    List<Product> findByName(String name);
}
