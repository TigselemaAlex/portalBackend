package com.example.portalbackend.api.dto.request.residence;

import jakarta.validation.constraints.NotNull;

public record ResidenceUpdateData(
        Long user
) {
}
