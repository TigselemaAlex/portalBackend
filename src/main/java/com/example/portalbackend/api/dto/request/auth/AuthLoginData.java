package com.example.portalbackend.api.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record AuthLoginData(
        @NotBlank(message = "El DNI no puede estar vacío")
        String dni,
        @NotBlank(message = "La contraseña no puede estar vacía")
        @Length(min = 6, max = 15,message = "La contraseña debe tener entre 6 y 15 caracteres")
        String password
) {
}
