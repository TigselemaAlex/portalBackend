package com.example.portalbackend.api.dto.request.auth;

import jakarta.validation.constraints.NotNull;

public record AuthDeviceData(
        @NotNull(message = "El usuario no puede ser nulo")
        Long userId,
        @NotNull(message = "El token del dispositivo no puede ser nulo")
        String deviceToken
) {
}
