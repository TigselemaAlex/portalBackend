package com.example.portalbackend.api.dto.response.incident_evidence;

import com.example.portalbackend.domain.entity.IncidentEvidence;

public record IncidentEvidenceResponse(
        Long id,
        String fileUrl
) {
    public IncidentEvidenceResponse(IncidentEvidence incidentEvidence) {
        this(
                incidentEvidence.getId(),
                incidentEvidence.getFileUrl()
        );
    }
}
