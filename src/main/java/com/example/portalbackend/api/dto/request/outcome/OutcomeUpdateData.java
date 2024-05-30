package com.example.portalbackend.api.dto.request.outcome;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Calendar;

public record OutcomeUpdateData(
        @NotBlank(message = "La descripción no puede estar vacía")
        String description,
        @NotNull(message = "El monto no puede estar vacío")
        BigDecimal amount,
        @NotNull(message = "La fecha de pago no puede estar vacía")
        Long paidDate,
        Long provider,
        @NotNull(message = "El tipo de egreso no puede estar vacío")
        Long type,
        MultipartFile paidEvidence
) {
}
