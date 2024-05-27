package com.example.portalbackend.api.dto.request.parking;

import com.example.portalbackend.util.enumerate.ParkingStatus;
import jakarta.validation.constraints.NotBlank;

public record ParkingCreateData(
        @NotBlank(message = "El código no puede estar vacío")
        String code,
        @NotBlank(message = "El estado no puede estar vacío")
        ParkingStatus status,
        @NotBlank(message = "El grupo no puede estar vacío")
        Long group
) {
}
