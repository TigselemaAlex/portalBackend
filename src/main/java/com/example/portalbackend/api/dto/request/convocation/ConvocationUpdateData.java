package com.example.portalbackend.api.dto.request.convocation;

import com.example.portalbackend.util.enumerate.ConvocationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConvocationUpdateData(
        @NotBlank(message = "El asunto no puede estar vacío")
        String subject,
        @NotBlank(message = "La descripción no puede estar vacía")
        String description,
        @NotBlank(message = "El lugar no puede estar vacío")
        String place,
        @NotNull(message = "La fecha no puede estar vacía")
        Long date,
        @NotNull(message = "El tipo no puede estar vacío")
        ConvocationType type,

        Long attendanceDeadline,
        @NotNull(message = "El usuario actualizador no puede estar vacío")
        Long updatedBy,
        @NotNull(message = "El estado finalizado no puede estar vacío")
        Boolean finalized
) {
}
