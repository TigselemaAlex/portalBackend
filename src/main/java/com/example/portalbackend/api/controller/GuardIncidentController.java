package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.guard_incident.GuardIncidentCreateData;
import com.example.portalbackend.api.dto.request.guard_incident.GuardIncidentUpdateData;
import com.example.portalbackend.api.usecase.GuardIncidentUseCase;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.util.enumerate.GuardIncidentStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("protected/guard-incidents")
@RequiredArgsConstructor
public class GuardIncidentController {

    private final GuardIncidentUseCase guardIncidentUseCase;

    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAllGuardIncidents(
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) Long date,
            @RequestParam(required = false) Long guard,
            @RequestParam(required = false) Long type,
            @RequestParam(required = false) GuardIncidentStatus status,
            @PageableDefault(
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable
    ){
        return guardIncidentUseCase.findAllGuardIncidents(subject, date, guard, type, status, pageable);
    }

    @PostMapping
    public ResponseEntity<CustomResponse<?>> createGuardIncident(
            @RequestParam String subject,
            @RequestParam Long date,
            @RequestParam Long guard,
            @RequestParam Long type,
            @RequestParam(required = false) List<MultipartFile> files,
            @RequestParam String description
            ){
        return guardIncidentUseCase.createGuardIncident(new GuardIncidentCreateData(subject,description,date,guard,type,files));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> updateGuardIncident(
            @PathVariable Long id,
            @RequestParam String subject,
            @RequestParam String description,
            @RequestParam Long date,
            @RequestParam Long guard,
            @RequestParam Long type,
            @RequestParam GuardIncidentStatus status,
            @RequestParam(required = false) List<Long> deletedFiles,
            @RequestParam(required = false) List<MultipartFile> newFiles,
            @RequestParam(required = false) String observation
    ){
        return guardIncidentUseCase.updateGuardIncident(id, new GuardIncidentUpdateData(subject, description, date, guard, type, status, deletedFiles, newFiles, observation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> deleteGuardIncident(@PathVariable Long id){
        return guardIncidentUseCase.deleteGuardIncident(id);
    }
}
