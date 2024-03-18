package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.parking.ParkingCreateData;
import com.example.portalbackend.api.dto.request.parking.ParkingUpdateData;
import com.example.portalbackend.domain.entity.Parking;
import com.example.portalbackend.domain.entity.Residence;
import com.example.portalbackend.domain.repository.ParkingRepository;
import com.example.portalbackend.service.spec.IParkingGroupService;
import com.example.portalbackend.service.spec.IParkingService;
import com.example.portalbackend.service.spec.IResidenceService;
import com.example.portalbackend.util.enumerate.ParkingStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ParkingService implements IParkingService {

    private final ParkingRepository parkingRepository;
    private final IParkingGroupService parkingGroupService;

    private final IResidenceService residenceService;

    public ParkingService(ParkingRepository parkingRepository, IParkingGroupService parkingGroupService, IResidenceService residenceService) {
        this.parkingRepository = parkingRepository;
        this.parkingGroupService = parkingGroupService;
        this.residenceService = residenceService;
    }

    @Override
    public Parking create(ParkingCreateData data) {
        Parking parking = Parking.builder()
                .status(data.status())
                .code(data.code())
                .group(parkingGroupService.findById(data.group()))
                .build();
        return parkingRepository.save(parking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Parking> findByGroup(Long groupId) {
        return parkingRepository.findByGroupOrderById(parkingGroupService.findById(groupId));
    }

    @Override
    public Parking findById(Long id) {
        return parkingRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Parking update(ParkingUpdateData data, Long id) {
        Parking parking = findById(id);
        if (data.residence() == null) {
            parking.setResidence(null);
            parking.setStatus(ParkingStatus.AVAILABLE);
        }else{
            parking.setResidence(residenceService.findById(data.residence()));
            parking.setStatus(ParkingStatus.OCCUPIED);
        }
        return parkingRepository.save(parking);
    }
}
