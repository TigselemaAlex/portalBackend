package com.example.portalbackend.api.dto.request.geolocation;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record GeolocationData(
        @NotNull(message = "La latitude no puede ser nula")
        BigDecimal latitude,
        @NotNull(message = "La longitud no puede ser nula")
        BigDecimal longitude,
        @NotNull(message = "El radio no puede ser nulo")
        Integer radius
) {
}
