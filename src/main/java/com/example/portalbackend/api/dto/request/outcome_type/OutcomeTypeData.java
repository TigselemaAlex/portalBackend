package com.example.portalbackend.api.dto.request.outcome_type;

import jakarta.validation.constraints.NotBlank;

public record OutcomeTypeData(
        @NotBlank
        String name,
        String description
) {
}
