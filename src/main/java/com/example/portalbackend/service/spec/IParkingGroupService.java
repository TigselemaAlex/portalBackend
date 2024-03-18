package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.parking_group.ParkingGroupCreateData;
import com.example.portalbackend.domain.entity.ParkingGroup;

import java.util.List;

public interface IParkingGroupService {
    ParkingGroup create(ParkingGroupCreateData data);
    List<ParkingGroup> findAll();

    ParkingGroup findById(Long id);
}
