package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.auth.AuthLoginData;
import com.example.portalbackend.api.dto.response.jwt.JwtResponse;
import com.example.portalbackend.config.security.dto.CustomUserDetails;
import com.example.portalbackend.config.security.jwt.JwtProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthUseCase {

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    public AuthUseCase(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public ResponseEntity<JwtResponse> login(AuthLoginData authLoginData) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authLoginData.dni(),
                                authLoginData.password()
                        )
                );
        if (authentication.isAuthenticated()) {
            JwtResponse response = new JwtResponse(
                    (CustomUserDetails)authentication.getPrincipal(),
                    jwtProvider.generateToken(authLoginData.dni())
            );
            return ResponseEntity.ok(response);
        }else{
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
