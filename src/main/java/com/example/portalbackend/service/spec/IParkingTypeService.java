package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.parking_type.ParkingTypeCreateData;
import com.example.portalbackend.api.dto.request.parking_type.ParkingTypeUpdateData;
import com.example.portalbackend.domain.entity.ParkingType;

import java.util.List;

public interface IParkingTypeService {
    ParkingType create(ParkingTypeCreateData data);

    ParkingType update(ParkingTypeUpdateData data, Long id);

    ParkingType findById(Long id);

    List<ParkingType> findAll();
}
