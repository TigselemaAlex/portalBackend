package com.example.portalbackend.api.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record UserUpdateData(
        @NotBlank(message = "Los nombres no pueden estar vacíos")
     String names,
     @NotBlank(message = "Los apellidos no pueden estar vacíos")
     String surnames,
        @NotBlank(message = "El email no puede estar vacío")
     String email,
    @NotBlank(message = "El teléfono no puede estar vacío")
            @Length(min =
                    10, max = 10, message = "El teléfono debe tener 10 dígitos")
     String phone,
    @NotBlank(message = "El DNI no puede estar vacío")
            @Length(min = 8, max = 15, message = "El DNI debe tener de 8 a 15 caracteres")
     String dni,

     @NotNull(message = "El estado no puede estar vacío")
     Boolean active,
     @Size(min = 1, message = "Debe seleccionar al menos un rol")
     List<Long> roles
) {}
