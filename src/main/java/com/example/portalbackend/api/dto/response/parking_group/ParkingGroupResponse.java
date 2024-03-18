package com.example.portalbackend.api.dto.response.parking_group;

import com.example.portalbackend.api.dto.response.parking_type.ParkingTypeResponse;
import com.example.portalbackend.domain.entity.ParkingGroup;

public record ParkingGroupResponse(
        Long id,
        String code,
        String x,
        String y,
        ParkingTypeResponse type
) {

    public ParkingGroupResponse(ParkingGroup parkingGroup) {
        this(
                parkingGroup.getId(),
                parkingGroup.getCode(),
                parkingGroup.getX(),
                parkingGroup.getY(),
                new ParkingTypeResponse(parkingGroup.getType())
        );
    }
}
