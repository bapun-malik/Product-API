package com.amazon.product.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazon.product.auth.model.RefreshToken;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Integer>{
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
