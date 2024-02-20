package com.example.portalbackend.api.dto.response.residence;

import com.example.portalbackend.api.dto.response.passage.PassageResponse;
import com.example.portalbackend.api.dto.response.user.UserResponse;
import com.example.portalbackend.domain.entity.Residence;

public record ResidenceResponse(
        Long id,
        String number,
        UserResponse user,
        PassageResponse passage
) {
    public ResidenceResponse(Residence residence){
        this(
                residence.getId(),
                residence.getNumber(),
                new UserResponse(residence.getUser()),
                new PassageResponse(residence.getPassage())
        );
    }
}
