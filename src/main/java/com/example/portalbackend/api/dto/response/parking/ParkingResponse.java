package com.example.portalbackend.api.dto.response.parking;

import com.example.portalbackend.api.dto.response.residence.ResidenceResponse;
import com.example.portalbackend.domain.entity.Parking;
import com.example.portalbackend.util.enumerate.ParkingStatus;

public record ParkingResponse(
        Long id,
        String code,
        ParkingStatus status,
        ResidenceResponse residence
) {

    public ParkingResponse(Parking parking) {
        this(
                parking.getId(),
                parking.getCode(),
                parking.getStatus(),
                parking.getResidence() != null ? new ResidenceResponse(parking.getResidence()) : null
        );
    }
}
