package com.example.portalbackend.api.dto.request.parking_group;

public record ParkingGroupCreateData(
        String code,
        String x,
        String y,
        Long type
) {
}
