package com.example.portalbackend.api.dto.request.income_type;

import com.example.portalbackend.util.enumerate.IncomeTypePeriod;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record IncomeTypeCreateData(
        @NotNull(message = "El nombre no puede ser nulo")
        String name,
        String description,
        @NotNull(message = "El periodo no puede ser nulo")
        IncomeTypePeriod period,
        @NotNull(message = "El precio no puede ser nulo")
        BigDecimal price
) {
}
