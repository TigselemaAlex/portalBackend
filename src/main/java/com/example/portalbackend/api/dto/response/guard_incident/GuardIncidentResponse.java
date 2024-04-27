package com.example.portalbackend.api.dto.response.guard_incident;

import com.example.portalbackend.api.dto.response.guard.GuardResponse;
import com.example.portalbackend.api.dto.response.incident_evidence.IncidentEvidenceResponse;
import com.example.portalbackend.api.dto.response.incident_type.IncidentTypeResponse;
import com.example.portalbackend.domain.entity.GuardIncident;
import com.example.portalbackend.util.enumerate.GuardIncidentStatus;

import java.util.Calendar;
import java.util.List;

public record GuardIncidentResponse(
        Long id,
        String subject,
        String description,
        Calendar date,
        GuardIncidentStatus status,
        String observation,
        GuardResponse guard,
        IncidentTypeResponse type,
        List<IncidentEvidenceResponse> evidences
) {
        public GuardIncidentResponse(GuardIncident guardIncident) {
            this(
                    guardIncident.getId(),
                    guardIncident.getSubject(),
                    guardIncident.getDescription(),
                    guardIncident.getDate(),
                    guardIncident.getStatus(),
                    guardIncident.getObservation(),
                    guardIncident.getGuard() == null ? null :
                    new GuardResponse(guardIncident.getGuard()),
                    guardIncident.getType() == null ? null :
                    new IncidentTypeResponse(guardIncident.getType()),
                    guardIncident.getEvidences() == null ? null : guardIncident.getEvidences().stream().map(IncidentEvidenceResponse::new).toList()
            );
        }
}
