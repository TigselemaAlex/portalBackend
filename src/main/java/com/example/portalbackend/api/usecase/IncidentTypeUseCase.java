package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.incident_type.IncidentTypeCreateData;
import com.example.portalbackend.api.dto.request.incident_type.IncidentTypeUpdateData;
import com.example.portalbackend.api.dto.response.incident_type.IncidentTypeResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.service.spec.IIncidentTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IncidentTypeUseCase extends AbstractUseCase{

    private final IIncidentTypeService incidentTypeService;

    protected IncidentTypeUseCase(CustomResponseBuilder customResponseBuilder, IIncidentTypeService incidentTypeService) {
        super(customResponseBuilder);
        this.incidentTypeService = incidentTypeService;
    }

    public ResponseEntity<CustomResponse<?>> findAllIncidentTypes() {
        List<IncidentTypeResponse> responses = incidentTypeService.getAllIncidentTypes().stream()
                .map(IncidentTypeResponse::new)
                .toList();
        return customResponseBuilder.build(HttpStatus.OK, "Tipos de incidentes recuperados exitosamente", responses);
    }

    public ResponseEntity<CustomResponse<?>> findActiveIncidentTypes() {
        List<IncidentTypeResponse> responses = incidentTypeService.getActiveIncidentTypes().stream()
                .map(IncidentTypeResponse::new)
                .toList();
        return customResponseBuilder.build(HttpStatus.OK, "Tipos de incidentes activos recuperados exitosamente", responses);
    }

    public ResponseEntity<CustomResponse<?>> findIncidentTypeById(Long id) {
        IncidentTypeResponse response = new IncidentTypeResponse(incidentTypeService.getIncidentTypeById(id));
        return customResponseBuilder.build(HttpStatus.OK, "Tipo de incidente recuperado exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> addIncidentType(IncidentTypeCreateData data) {
        IncidentTypeResponse response = new IncidentTypeResponse(incidentTypeService.addIncidentType(data));
        return customResponseBuilder.build(HttpStatus.CREATED, "Tipo de incidente creado exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> updateIncidentType(Long id, IncidentTypeUpdateData data) {
        IncidentTypeResponse response = new IncidentTypeResponse(incidentTypeService.updateIncidentType(id, data));
        return customResponseBuilder.build(HttpStatus.OK, "Tipo de incidente actualizado exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> deleteIncidentType(Long id) {
        incidentTypeService.deleteIncidentType(id);
        return customResponseBuilder.build(HttpStatus.OK, "Tipo de incidente inhabilitado exitosamente");
    }

    public ResponseEntity<CustomResponse<?>> reactivateIncidentType(Long id) {
        incidentTypeService.reactivateIncidentType(id);
        return customResponseBuilder.build(HttpStatus.OK, "Tipo de incidente reactivado exitosamente");
    }
}
