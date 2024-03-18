package com.example.portalbackend.api.dto.response.parking_type;

import com.example.portalbackend.domain.entity.ParkingType;

import java.math.BigDecimal;

public record ParkingTypeResponse(
        Long id,
        String type,
        String description,
        BigDecimal price,
        String severity
) {

    public ParkingTypeResponse(ParkingType parkingType) {
        this(
                parkingType.getId(),
                parkingType.getType(),
                parkingType.getDescription(),
                parkingType.getPrice(),
                parkingType.getSeverity()
        );
    }
}
