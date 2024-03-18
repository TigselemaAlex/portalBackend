package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.response.parking_group.ParkingGroupResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.service.spec.IParkingGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParkingGroupUseCase extends AbstractUseCase {

    private final IParkingGroupService parkingGroupService;
    protected ParkingGroupUseCase(CustomResponseBuilder customResponseBuilder, IParkingGroupService parkingGroupService) {
        super(customResponseBuilder);
        this.parkingGroupService = parkingGroupService;
    }

    public ResponseEntity<CustomResponse<?>> findAll() {
        List<ParkingGroupResponse> responses = parkingGroupService.findAll().stream().map(ParkingGroupResponse::new).toList();
        return customResponseBuilder.build(HttpStatus.OK, "Grupos de estacionamiento encontrados", responses);
    }
}
