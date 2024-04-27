package com.example.portalbackend.api.dto.request.incident_type;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IncidentTypeUpdateData(
        @NotBlank(message = "El nombre del tipo de incidente no puede estar vacío")
        String name,
        @NotBlank(message = "La descripción del tipo de incidente no puede estar vacía")
        String description,
        @NotBlank(message = "La severidad del tipo de incidente no puede estar vacía")
        String severity,
        @NotNull(message = "El estado del tipo de incidente no puede estar vacío")
        Boolean active
) {
}
