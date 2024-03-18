package com.example.portalbackend.api.dto.request.parking_type;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ParkingTypeUpdateData(
        @NotBlank(message = "El precio de parqueadero es requerido")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor o igual a 0.01")
        BigDecimal price,

        @NotBlank(message = "La descripción del parqueadero no puede estar vacía")
        String description
) {
}
