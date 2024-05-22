package com.example.portalbackend.api.dto.response.income_type;

import com.example.portalbackend.domain.entity.IncomeType;
import com.example.portalbackend.util.enumerate.IncomeTypePeriod;

import java.math.BigDecimal;

public record IncomeTypeResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        IncomeTypePeriod period,
        Boolean active,
        Boolean canBeDeleted
) {
    public IncomeTypeResponse(IncomeType incomeType) {
        this(
                incomeType.getId(),
                incomeType.getName(),
                incomeType.getDescription(),
                incomeType.getPrice(),
                incomeType.getPeriod(),
                incomeType.getActive(),
                incomeType.getCanBeDeleted()
        );
    }
}
