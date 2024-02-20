package com.example.portalbackend.api.dto.request.passage;

import jakarta.validation.constraints.NotBlank;

public record PassageCreateData(
        @NotBlank (message = "El nombre no puede estar vacío")
        String name
) {
}
