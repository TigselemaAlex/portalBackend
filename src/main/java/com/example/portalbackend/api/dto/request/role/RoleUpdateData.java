package com.example.portalbackend.api.dto.request.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoleUpdateData(
        @NotBlank (message = "La descripción no puede estar vacía")
        String description,
        @NotNull(message = "El estado no puede estar vacio")
        Boolean active
) {
}
