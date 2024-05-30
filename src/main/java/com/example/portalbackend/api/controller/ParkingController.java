package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.parking.ParkingUpdateData;
import com.example.portalbackend.api.dto.request.parking_type.ParkingTypeUpdateData;
import com.example.portalbackend.api.usecase.ParkingUseCase;
import com.example.portalbackend.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/protected/parkings")
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingUseCase parkingUseCase;


    @GetMapping("/{groupId}")
    public ResponseEntity<CustomResponse<?>> findAllByGroup(@PathVariable Long groupId) {
        return parkingUseCase.findByGroup(groupId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> update(@PathVariable Long id, @RequestBody ParkingUpdateData data) {
        return parkingUseCase.update(id, data);
    }

    @GetMapping("/types")
    public ResponseEntity<CustomResponse<?>> findAllTypes() {
        return parkingUseCase.findAllParkingsType();
    }

    @PutMapping("/types/{id}")
    public ResponseEntity<CustomResponse<?>> updateType(@PathVariable Long id, @RequestBody ParkingTypeUpdateData data) {
        return parkingUseCase.updateParkingType(id, data);
    }

    @GetMapping("/residence/{id}")
    public ResponseEntity<CustomResponse<?>> findAllByResidence(@PathVariable Long id) {
        return parkingUseCase.findAllByResidence(id);
    }

}
