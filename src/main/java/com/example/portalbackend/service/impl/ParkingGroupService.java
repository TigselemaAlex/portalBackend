package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.parking_group.ParkingGroupCreateData;
import com.example.portalbackend.domain.entity.ParkingGroup;
import com.example.portalbackend.domain.repository.ParkingGroupRepository;
import com.example.portalbackend.service.spec.IParkingGroupService;
import com.example.portalbackend.service.spec.IParkingTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ParkingGroupService implements IParkingGroupService {
    private final ParkingGroupRepository parkingGroupRepository;

    private final IParkingTypeService parkingTypeService;

    public ParkingGroupService(ParkingGroupRepository parkingGroupRepository, IParkingTypeService parkingTypeService) {
        this.parkingGroupRepository = parkingGroupRepository;
        this.parkingTypeService = parkingTypeService;
    }

    @Override
    public ParkingGroup create(ParkingGroupCreateData data) {
        ParkingGroup parkingGroup = ParkingGroup.builder()
                .code(data.code())
                .type(parkingTypeService.findById(data.type()))
                .x(data.x())
                .y(data.y())
                .build();
        return parkingGroupRepository.save(parkingGroup);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingGroup> findAll() {
        return parkingGroupRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ParkingGroup findById(Long id) {
        return parkingGroupRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
