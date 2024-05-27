package com.example.portalbackend.api.dto.request.guard;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record GuardUpdateData(
        @NotBlank(message = "El nombre completo es requerido")
        String fullName,
        @NotBlank(message = "El DNI es requerido")
        @Length(min = 8, max = 15, message = "El DNI debe tener de 8 a 15 caracteres")
        String dni,
        MultipartFile photo,
        Boolean isPhotoUpdated,
        @NotBlank(message = "El teléfono es requerido")
        @Length(min = 6, max = 15, message = "El teléfono debe tener de 6 a 15 dígitos")
        String phone
) { }
