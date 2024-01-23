package com.amazon.product.auth.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.amazon.product.auth.model.User;
import com.amazon.product.auth.model.UserRole;
import com.amazon.product.auth.repository.UserRepo;
import com.amazon.product.auth.utils.AuthResponse;
import com.amazon.product.auth.utils.RegisterRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo repo;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    private AuthResponse register(RegisterRequest registerRequest){
        var user=User.builder()
                    .name(registerRequest.getName())
                    .email(registerRequest.getEmail())
                    .username(registerRequest.getPassword())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(UserRole.USER)
                    .build();
        User savedUser=repo.save(user);
        var accessToken=jwtService.generateToken(savedUser);
        var refreshToken=refreshTokenService.createRefreshToken(savedUser.getEmail());
        return AuthResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken.getRefreshToken())
                            .build();
    }
}
