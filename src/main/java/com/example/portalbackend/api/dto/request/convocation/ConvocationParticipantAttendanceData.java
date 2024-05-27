package com.example.portalbackend.api.dto.request.convocation;

import jakarta.validation.constraints.NotNull;
import org.aspectj.weaver.ast.Not;

import java.math.BigDecimal;

public record ConvocationParticipantAttendanceData(

        @NotNull
        BigDecimal latitude,
        @NotNull
        BigDecimal longitude,
        @NotNull Long residence,
        @NotNull String deviceId
) {
}
