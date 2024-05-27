package com.example.portalbackend.api.dto.response.penalty_type;

import com.example.portalbackend.domain.entity.PenaltyType;

import java.math.BigDecimal;

public record PenaltyTypeResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Boolean canBeDeleted,
        Boolean active
) {
    public PenaltyTypeResponse(PenaltyType penaltyType) {
        this(
                penaltyType.getId(),
                penaltyType.getName(),
                penaltyType.getDescription(),
                penaltyType.getPrice(),
                penaltyType.getCanBeDeleted(),
                penaltyType.getActive()
        );

    }
}
