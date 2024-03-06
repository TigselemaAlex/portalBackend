package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.auth.AuthLoginData;
import com.example.portalbackend.api.dto.response.jwt.JwtResponse;
import com.example.portalbackend.api.usecase.AuthUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/auth")
public class AuthController {

    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody AuthLoginData authLoginData){
        return authUseCase.login(authLoginData);
    }
}
