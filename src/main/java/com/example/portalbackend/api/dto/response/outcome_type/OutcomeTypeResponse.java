package com.example.portalbackend.api.dto.response.outcome_type;

import com.example.portalbackend.domain.entity.OutcomeType;

public record OutcomeTypeResponse(
    Long id,
    String name,
    String description,
    Boolean active
) {

    public OutcomeTypeResponse(OutcomeType outcomeType) {
        this(outcomeType.getId(), outcomeType.getName(), outcomeType.getDescription(), outcomeType.getActive());
    }
}
