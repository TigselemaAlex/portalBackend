package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.guard_incident.GuardIncidentCreateData;
import com.example.portalbackend.api.dto.request.guard_incident.GuardIncidentUpdateData;
import com.example.portalbackend.api.dto.response.PageResponse;
import com.example.portalbackend.api.dto.response.guard_incident.GuardIncidentResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.service.spec.IGuardIncidentService;
import com.example.portalbackend.util.enumerate.GuardIncidentStatus;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class GuardIncidentUseCase extends AbstractUseCase{

    private final IGuardIncidentService guardIncidentService;
    protected GuardIncidentUseCase(CustomResponseBuilder customResponseBuilder, IGuardIncidentService guardIncidentService) {
        super(customResponseBuilder);
        this.guardIncidentService = guardIncidentService;
    }

    public ResponseEntity<CustomResponse<?>> findAllGuardIncidents(String subject, Long date, Long guard, Long type, GuardIncidentStatus status, Pageable pageable){
        Page<GuardIncidentResponse> responses = guardIncidentService.findAll(subject, date, guard, type, status, pageable)
                .map(GuardIncidentResponse::new);
        PageResponse pageResponse = new PageResponse(responses);
        return customResponseBuilder.build(HttpStatus.OK, "Listado de incidentes de guardias", pageResponse);
    }

    public ResponseEntity<CustomResponse<?>> createGuardIncident(@Valid @RequestBody GuardIncidentCreateData data){
        GuardIncidentResponse response = new GuardIncidentResponse(guardIncidentService.create(data));
        return customResponseBuilder.build(HttpStatus.CREATED, "Incidente de guardia creado", response);
    }

    public ResponseEntity<CustomResponse<?>> updateGuardIncident(Long id, GuardIncidentUpdateData data){
        GuardIncidentResponse response = new GuardIncidentResponse(guardIncidentService.update(id, data));
        return customResponseBuilder.build(HttpStatus.OK, "Incidente de guardia actualizado", response);
    }

    public ResponseEntity<CustomResponse<?>> deleteGuardIncident(Long id){
        guardIncidentService.delete(id);
        return customResponseBuilder.build(HttpStatus.OK, "Incidente de guardia eliminado", null);
    }
}
