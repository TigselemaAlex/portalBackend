package com.example.portalbackend.api.dto.request.guard_incident;

import com.example.portalbackend.util.enumerate.GuardIncidentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record GuardIncidentUpdateData(
        @NotBlank(message = "El asunto no puede estar vacío")
        String subject,
        @NotBlank(message = "La descripción no puede estar vacía")
        String  description,
        @NotNull(message = "La fecha no puede estar vacía")
        Long date,
        @NotNull(message = "El guardia no puede estar vacío")
        Long guard,
        @NotNull(message = "El tipo no puede estar vacío")
        Long type,
        @NotNull(message = "El estado no puede estar vacío")
        GuardIncidentStatus status,
        List<Long> deletedFiles,
        List<MultipartFile> newFiles,
        String observation
) {
}
