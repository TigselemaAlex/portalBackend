package com.example.portalbackend.api.dto.response.penalty_evidence;

import com.example.portalbackend.domain.entity.PenaltyEvidence;

public record PenaltyEvidenceResponse(
        Long id,
        String fileName,
        String fileUrl
) {
    public PenaltyEvidenceResponse(PenaltyEvidence evidence) {
        this(evidence.getId(), evidence.getFileName(), evidence.getFileUrl());
    }
}
