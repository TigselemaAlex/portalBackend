package com.example.portalbackend.api.dto.request.convocation;

import jakarta.validation.constraints.NotNull;

public record ConvocationAttendanceData(
        @NotNull(message = "La residencia es requerida")
        Long residence,
        @NotNull(message = "La asistencia es requerida")
        Boolean attendance,
        String participant,
        String deviceId
) {
}
