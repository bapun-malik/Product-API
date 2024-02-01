package com.amazon.product.auth.service;


import java.time.Instant;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.amazon.product.auth.model.RefreshToken;
import com.amazon.product.auth.model.User;
import com.amazon.product.auth.repository.RefreshTokenRepo;
import com.amazon.product.auth.repository.UserRepo;

@Service
public class RefreshTokenService {
    private UserRepo userRepo;
    private RefreshTokenRepo refreshTokenRepo;

    public RefreshTokenService(UserRepo userRepo,RefreshTokenRepo refreshTokenRepo){
        this.refreshTokenRepo=refreshTokenRepo;
        this.userRepo=userRepo;
    }
    public RefreshToken createRefreshToken(String username){
        User user=userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("username not found with email:"+username));
        RefreshToken refreshToken=user.getRefreshToken();
        if(refreshToken==null){
            long refreshTokenValidity=30*60*60*1000;
            refreshToken=RefreshToken
                            .builder()
                            .refreshToken(UUID.randomUUID().toString())
                            .expirationTime(Instant.now().plusMillis(refreshTokenValidity))
                            .user(user)
                            .build();
            refreshTokenRepo.save(refreshToken);  
        }
        return refreshToken;
    }

    public RefreshToken verifyRefreshRToken(String refreshToken){
        RefreshToken ref=refreshTokenRepo.findByRefreshToken(refreshToken)
                                         .orElseThrow(()->new RuntimeException("Refresh Token Not Found"));
        if(ref.getExpirationTime().compareTo(Instant.now())<0){
            refreshTokenRepo.delete(ref);
            throw new RuntimeException("refresh token expired");
        }
        return ref;
    }
}
