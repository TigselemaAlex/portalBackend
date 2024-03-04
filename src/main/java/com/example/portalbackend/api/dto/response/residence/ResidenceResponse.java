package com.example.portalbackend.api.dto.response.residence;

import com.example.portalbackend.api.dto.response.passage.PassageResponse;
import com.example.portalbackend.api.dto.response.user.UserResponse;
import com.example.portalbackend.domain.entity.Passage;
import com.example.portalbackend.domain.entity.Residence;
import com.example.portalbackend.domain.entity.User;

import java.time.Instant;

public record ResidenceResponse(
        Long id,
        String number,
        Instant createdAt,
        Instant updatedAt,
        UserResponse user,
        PassageResponse passage
) {
    public ResidenceResponse(Residence residence){
        this(
                residence.getId(),
                residence.getNumber(),
                residence.getCreatedAt(),
                residence.getUpdatedAt(),
                residence.getUser() != null ? new UserResponse(residence.getUser()) : null,
                residence.getPassage() != null ? new PassageResponse(residence.getPassage()) : null
        );
    }
}
