package com.example.portalbackend.api.dto.response.incident_type;

import com.example.portalbackend.domain.entity.IncidentType;

public record IncidentTypeResponse(
        Long id,
        String name,
        String severity,
        String description,
        Boolean active
) {

    public IncidentTypeResponse(IncidentType incidentType){
        this(
                incidentType.getId(),
                incidentType.getName(),
                incidentType.getSeverity(),
                incidentType.getDescription(),
                incidentType.getActive()
        );
    }
}
