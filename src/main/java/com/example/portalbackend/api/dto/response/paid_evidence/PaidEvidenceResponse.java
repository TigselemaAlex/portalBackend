package com.example.portalbackend.api.dto.response.paid_evidence;

import com.example.portalbackend.domain.entity.PaidEvidence;

public record PaidEvidenceResponse(
        Long id,
        String fileName,
        String fileUrl
) {
    public PaidEvidenceResponse(PaidEvidence evidence){
        this(
                evidence.getId(),
                evidence.getFileName(),
                evidence.getFileUrl()
        );
    }
}
