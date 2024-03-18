package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.usecase.ParkingGroupUseCase;
import com.example.portalbackend.common.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected/parking-group")
public class ParkingGroupController {

    private final ParkingGroupUseCase parkingGroupUseCase;

    public ParkingGroupController(ParkingGroupUseCase parkingGroupUseCase) {
        this.parkingGroupUseCase = parkingGroupUseCase;
    }

    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAll() {
        return parkingGroupUseCase.findAll();
    }
}
