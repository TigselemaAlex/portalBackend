package com.example.portalbackend.api.dto.request.residence;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResidenceCreateData(
        @NotBlank(message = "El número de la residencia no puede ser nulo")
        String number,
        Long user,
        @NotNull(message = "El pasaje no puede ser nulo")
        Long passage
) {
}
