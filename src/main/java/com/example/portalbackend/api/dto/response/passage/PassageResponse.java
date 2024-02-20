package com.example.portalbackend.api.dto.response.passage;

import com.example.portalbackend.domain.entity.Passage;

public record PassageResponse(
        Long id,
        String name,
        Boolean active,
        String createdAt,
        String updatedAt
) {

    public PassageResponse(Passage passage) {
        this(passage.getId(), passage.getName(), passage.getActive(), passage.getCreatedAt().toString(), passage.getUpdatedAt().toString());
    }
}
