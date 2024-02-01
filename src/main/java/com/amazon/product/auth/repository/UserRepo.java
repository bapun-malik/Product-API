package com.amazon.product.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazon.product.auth.model.User;

public interface UserRepo extends JpaRepository<User,Integer>  {
    public Optional<User> findByEmail(String email);
    public Optional<User> findByUsername(String userName);
}
