package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.parking.ParkingCreateData;
import com.example.portalbackend.api.dto.request.parking.ParkingUpdateData;
import com.example.portalbackend.domain.entity.Parking;
import com.example.portalbackend.domain.entity.Residence;

import java.util.List;

public interface IParkingService {

    Parking create(ParkingCreateData data);
    List<Parking> findByGroup(Long groupId);
    Parking findById(Long id);
    Parking update(ParkingUpdateData data, Long id);

    Parking findByIdAndResidence(Long id, Residence residence);

    List<Parking> findAllByResidence(Residence residence);
}
