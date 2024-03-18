package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.auth.AuthLoginData;
import com.example.portalbackend.api.dto.response.jwt.JwtResponse;
import com.example.portalbackend.api.dto.response.mail.MailResponse;
import com.example.portalbackend.api.usecase.AuthUseCase;
import com.example.portalbackend.api.usecase.UserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/public/auth")
public class AuthController {

    private final AuthUseCase authUseCase;

    private final UserUseCase userUseCase;


    public AuthController(AuthUseCase authUseCase, UserUseCase userUseCase) {
        this.authUseCase = authUseCase;
        this.userUseCase = userUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody AuthLoginData authLoginData){
        return authUseCase.login(authLoginData);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<HashMap<String, Boolean>> validateToken(){
        return authUseCase.validateToken();
    }

    @PatchMapping("/recover-password/{dni}")
    public ResponseEntity<MailResponse> recoverPassword(@PathVariable String dni){
        return userUseCase.recoverPassword(dni);
    }

}
