package com.example.portalbackend.api.dto.request.income;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Calendar;

public record IncomeFeesCreateData(
        @NotEmpty(message = "Descripción es requerida")
        String description,
        BigDecimal amount,
        @NotNull(message = "Fecha de pago es requerida")
        Long paidDate,
        @NotNull(message = "Fecha de pago desde es requerido")
        Long paidSince,
        @NotNull(message = "Fecha de pago hasta es requerida")
        Long paidUntil,
        @NotNull(message = "Residencia es requerida")
        Long residence,
        Long parking,
        @NotNull(message = "Tipo de ingreso es requerido")
        Long incomeType,
        @NotNull(message = "Evidencia de pago es requerida")
        MultipartFile paidEvidence
) {
}
