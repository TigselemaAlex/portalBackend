package com.example.portalbackend.api.dto.request.convocation;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ConvocationParticipantAttendanceData(

        @NotNull
        BigDecimal latitude,
        @NotNull
        BigDecimal longitude,
        @NotNull Long residence
) {
}
