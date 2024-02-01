package com.amazon.product.auth.service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.amazon.product.auth.model.User;
import com.amazon.product.auth.model.UserRole;
import com.amazon.product.auth.repository.UserRepo;
import com.amazon.product.auth.utils.AuthResponse;
import com.amazon.product.auth.utils.LoginRequest;
import com.amazon.product.auth.utils.RegisterRequest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final PasswordEncoder passwordEncoder;
    private final UserRepo repo;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    
    public AuthResponse register(RegisterRequest registerRequest){
        var user=User.builder()
                    .name(registerRequest.getName())
                    .email(registerRequest.getEmail())
                    .username(registerRequest.getUsername())
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

    public AuthResponse login(LoginRequest loginRequest){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        var user=repo.findByEmail(loginRequest.getEmail()).orElseThrow(()->new UsernameNotFoundException("no user exists"));
        var accessToken=jwtService.generateToken(user);
        var refreshToken=refreshTokenService.createRefreshToken(loginRequest.getEmail());
        return AuthResponse.builder()
                           .accessToken(accessToken)
                           .refreshToken(refreshToken.getRefreshToken())
                           .build();
    }
}
