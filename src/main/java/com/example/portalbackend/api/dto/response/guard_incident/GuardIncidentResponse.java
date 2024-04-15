package com.example.portalbackend.api.dto.response.guard_incident;

import com.example.portalbackend.api.dto.response.guard.GuardResponse;
import com.example.portalbackend.api.dto.response.incident_type.IncidentTypeResponse;
import com.example.portalbackend.domain.entity.GuardIncident;
import com.example.portalbackend.util.enumerate.GuardIncidentStatus;

import java.util.Calendar;

public record GuardIncidentResponse(
        Long id,
        String subject,
        String description,
        Calendar date,
        GuardIncidentStatus status,
        String observation,
        GuardResponse guard,
        IncidentTypeResponse type

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
                    new IncidentTypeResponse(guardIncident.getType())
            );
        }
}
