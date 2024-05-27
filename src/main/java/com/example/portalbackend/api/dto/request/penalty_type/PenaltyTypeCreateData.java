package com.example.portalbackend.api.dto.request.penalty_type;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PenaltyTypeCreateData(
        @NotEmpty(message = "El nombre no puede estar vacío")
        String name,
        @NotEmpty(message = "La descripción no puede estar vacía")
        String description,
        @NotNull(message = "El precio no puede estar vacío")
        BigDecimal price
) {
}
