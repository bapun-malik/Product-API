package com.amazon.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.product.auth.utils.AuthResponse;
import com.amazon.product.auth.utils.RegisterRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest){
        
    }

}
