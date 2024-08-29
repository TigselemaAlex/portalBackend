package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.penalty_type.PenaltyTypeCreateData;
import com.example.portalbackend.api.dto.request.penalty_type.PenaltyTypeUpdateData;
import com.example.portalbackend.api.usecase.PenaltyTypeUseCase;
import com.example.portalbackend.common.CustomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/protected/penalty-type")
@PreAuthorize("hasAnyRole('ROLE_PRESIDENT', 'ROLE_VICEPRESIDENT', 'ROLE_TREASURER', 'ROLE_ADMIN', 'ROLE_SECRETARY')")
public class PenaltyTypeController {

    private final PenaltyTypeUseCase penaltyTypeUseCase;

    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAll(){
        return penaltyTypeUseCase.findAll();
    }

    @GetMapping("/active")
    public ResponseEntity<CustomResponse<?>> findAllActive(){
        return penaltyTypeUseCase.findAllActive();
    }

    @PostMapping
    public ResponseEntity<CustomResponse<?>> create(@Valid @RequestBody PenaltyTypeCreateData data){
        return penaltyTypeUseCase.create(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> update(@PathVariable Long id, @Valid @RequestBody PenaltyTypeUpdateData data){
        return penaltyTypeUseCase.update(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> delete(@PathVariable Long id){
        return penaltyTypeUseCase.delete(id);
    }

    @PutMapping("/{id}/reactivate")
    public ResponseEntity<CustomResponse<?>> reactivate(@PathVariable Long id){
        return penaltyTypeUseCase.reactivate(id);
    }
}
