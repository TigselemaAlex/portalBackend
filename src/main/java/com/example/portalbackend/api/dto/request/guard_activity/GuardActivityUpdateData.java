package com.example.portalbackend.api.dto.request.guard_activity;

import com.example.portalbackend.util.enumerate.GuardActivityStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Calendar;

public record GuardActivityUpdateData(
        @NotBlank(message = "El asunto no puede estar vacío")
        String subject,
        @NotBlank(message = "La descripción no puede estar vacía")
        String description,
        @NotNull(message = "La fecha de inicio no puede estar vacía")
        Long startDate,
        @NotNull(message = "La fecha de fin no puede estar vacía")
        Long endDate,
        @NotNull(message = "El guardia no puede estar vacío")
        Long guard,
        String observation,
        @NotNull(message = "El estado no puede estar vacío")
        GuardActivityStatus status
) { }
