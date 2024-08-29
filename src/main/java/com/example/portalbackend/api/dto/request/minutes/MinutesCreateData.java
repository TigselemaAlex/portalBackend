package com.example.portalbackend.api.dto.request.minutes;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record MinutesCreateData(
        @NotNull
        Long convocationId,
        @NotNull
        MultipartFile file
) {
}
