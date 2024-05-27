package com.example.portalbackend.api.dto.request.provider;

import jakarta.validation.constraints.NotBlank;

public record ProviderCreateData(
        @NotBlank(message = "El nombre es requerido")
        String name,
    String description,
    @NotBlank(message = "La dirección es requerida")
    String address,
    @NotBlank(message = "El RUC es requerido")
    String ruc,
    @NotBlank(message = "El teléfono es requerido")
    String phone,
    @NotBlank(message = "El correo es requerido")
    String email,
    String website
) {
}
