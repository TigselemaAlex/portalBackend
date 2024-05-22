package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.parking_type.ParkingTypeCreateData;
import com.example.portalbackend.api.dto.request.parking_type.ParkingTypeUpdateData;
import com.example.portalbackend.domain.entity.ParkingType;
import com.example.portalbackend.domain.repository.ParkingTypeRepository;
import com.example.portalbackend.service.spec.IParkingTypeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ParkingTypeService implements IParkingTypeService {

    private final ParkingTypeRepository parkingTypeRepository;


    @Override
    public ParkingType create(ParkingTypeCreateData data) {
        ParkingType parkingType = ParkingType.builder()
                .type(data.type())
                .description(data.description())
                .severity(data.severity())
                .build();
        return parkingTypeRepository.save(parkingType);
    }

    @Override
    public ParkingType update(ParkingTypeUpdateData data, Long id) {
        ParkingType parkingType = findById(id);
        parkingType.setDescription(data.description());
        return parkingTypeRepository.save(parkingType);
    }

    @Override
    @Transactional(readOnly = true)
    public ParkingType findById(Long id) {
        return parkingTypeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<ParkingType> findAll() {
        return parkingTypeRepository.findAll();
    }
}
