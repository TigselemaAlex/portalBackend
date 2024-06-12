package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.usecase.ParkingGroupUseCase;
import com.example.portalbackend.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected/parking-group")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_PRESIDENT', 'ROLE_VICEPRESIDENT', 'ROLE_TREASURER', 'ROLE_ADMIN', 'ROLE_SECRETARY')")
public class ParkingGroupController {

    private final ParkingGroupUseCase parkingGroupUseCase;


    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAll() {
        return parkingGroupUseCase.findAll();
    }
}
