package com.amazon.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.product.auth.model.RefreshToken;
import com.amazon.product.auth.model.User;
import com.amazon.product.auth.service.AuthService;
import com.amazon.product.auth.service.JwtService;
import com.amazon.product.auth.service.RefreshTokenService;
import com.amazon.product.auth.utils.AuthResponse;
import com.amazon.product.auth.utils.LoginRequest;
import com.amazon.product.auth.utils.RefreshTokenRequest;
import com.amazon.product.auth.utils.RegisterRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    public AuthController(AuthService authService,RefreshTokenService refreshTokenService,JwtService jwtService){
        this.authService=authService;
        this.refreshTokenService=refreshTokenService;
        this.jwtService=jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        RefreshToken refreshToken=refreshTokenService.verifyRefreshRToken(refreshTokenRequest.getRefreshToken());
        User user=refreshToken.getUser();
        String accessToken=jwtService.generateToken(user);
        return ResponseEntity.ok(AuthResponse.builder()
                              .accessToken(accessToken)
                              .refreshToken(refreshToken.getRefreshToken())
                              .build()
                                );
    }
}
