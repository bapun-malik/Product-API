package com.amazon.product.auth.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthFilterService extends OncePerRequestFilter{

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws IOException, ServletException{
        final String authHeader=request.getHeader("Authorization");
        String jwt;
        String username;
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        //extract jwt
        jwt=authHeader.substring(7);
        //extract username from jwt
        username=jwtService.extractUsername(jwt);
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=userService.loadUserByUsername(username);
            if(jwtService.isTokenValid(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                                                                            userDetails,
                                                                            null,
                                                                            userDetails.getAuthorities()
                                                                            );
                authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );  
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); 
            }
        }
        filterChain.doFilter(request, response);
    }
}
