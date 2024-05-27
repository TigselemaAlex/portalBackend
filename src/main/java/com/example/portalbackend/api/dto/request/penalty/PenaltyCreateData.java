package com.example.portalbackend.api.dto.request.penalty;

import com.example.portalbackend.util.enumerate.PaidStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public record PenaltyCreateData(
        @NotBlank(message = "La descripción no puede estar vacía")
        String description,
        @NotNull(message = "La fecha no puede estar vacía")
        Long issueDate,
        Long paidDate,
        @NotNull(message = "El monto no puede estar vacío")
        BigDecimal amount,
        @NotNull(message = "La residencia no puede estar vacía")
        Long residence,
        @NotNull(message = "El estado no puede estar vacío")
        PaidStatus status,
        @NotNull(message = "El tipo no puede estar vacío")
        Long type,
        List<MultipartFile> files,
        MultipartFile paidEvidence
) {
}
