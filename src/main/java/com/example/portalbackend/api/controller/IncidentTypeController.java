package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.incident_type.IncidentTypeCreateData;
import com.example.portalbackend.api.dto.request.incident_type.IncidentTypeUpdateData;
import com.example.portalbackend.api.usecase.IncidentTypeUseCase;
import com.example.portalbackend.common.CustomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/protected/incident-type")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_PRESIDENT', 'ROLE_VICEPRESIDENT', 'ROLE_TREASURER', 'ROLE_ADMIN', 'ROLE_SECRETARY')")
public class IncidentTypeController {

    private final IncidentTypeUseCase incidentTypeUseCase;

    @GetMapping
    public ResponseEntity<CustomResponse<?>> getIncidentTypes() {
        return incidentTypeUseCase.findAllIncidentTypes();
    }

    @GetMapping("/active")
    public ResponseEntity<CustomResponse<?>> getActiveIncidentTypes() {
        return incidentTypeUseCase.findActiveIncidentTypes();
    }

    @PostMapping
    public ResponseEntity<CustomResponse<?>> addIncidentType(@Valid @RequestBody IncidentTypeCreateData data) {
        return incidentTypeUseCase.addIncidentType(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> updateIncidentType(@PathVariable Long id, @Valid @RequestBody IncidentTypeUpdateData data) {
        return incidentTypeUseCase.updateIncidentType(id, data);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> deleteIncidentType(@PathVariable Long id) {
        return incidentTypeUseCase.deleteIncidentType(id);
    }

    @PutMapping("/{id}/reactivate")
    public ResponseEntity<CustomResponse<?>> reactivateIncidentType(@PathVariable Long id) {
        return incidentTypeUseCase.reactivateIncidentType(id);
    }

}
