package com.example.portalbackend.api.dto.request.social_event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;

public record SocialEventUpdateData(
        @NotBlank(message = "El título es requerido")
        String title,
        String description,
        @NotBlank(message = "El lugar es requerido")
        String place,

        @NotNull(message = "La fecha es requerida")
        Calendar date,
        MultipartFile image,
        Boolean isImageUpdated,
        @NotNull(message = "El usuario que actualiza es requerido")
        Long updatedBy
) {
}
