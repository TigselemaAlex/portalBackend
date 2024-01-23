package com.example.portalbackend.api.dto.request.role;

import jakarta.validation.constraints.NotBlank;

public record RoleUpdateData(
        @NotBlank (message = "La descripción no puede estar vacía")
        String description
) {
}
