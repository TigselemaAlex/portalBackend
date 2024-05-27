package com.example.portalbackend.api.dto.request.vote;

import jakarta.validation.constraints.NotNull;

public record AssemblyQuestionData(
        @NotNull(message = "La pregunta de la asamblea no puede ser nula")
        String question,
        @NotNull(message = "El creador de la pregunta de la asamblea no puede ser nulo")
        Long createdBy,
        @NotNull(message = "La convocatoria de la pregunta de la asamblea no puede ser nula")
        Long convocation
) {
}
