package com.example.portalbackend.api.dto.request.parking_type;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ParkingTypeCreateData(
        @NotBlank(message = "El tipo de parqueadero no puede estar vacío")
        String type,
        @NotBlank(message = "La descripción del parqueadero no puede estar vacía")
        String description,

        @NotBlank(message = "La severidad del parqueadero no puede estar vacía")
        String severity,
        @DecimalMin(value = "0.01" ,message = "El precio del parqueadero no puede ser negativo")
        @Digits(integer = 3, fraction = 2, message = "El precio del parqueadero no puede tener más de 3 enteros y 2 decimales")
        BigDecimal price
) {
}
