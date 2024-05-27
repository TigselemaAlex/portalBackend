package com.example.portalbackend.api.dto.response.penalty;

import com.example.portalbackend.api.dto.response.paid_evidence.PaidEvidenceResponse;
import com.example.portalbackend.api.dto.response.penalty_evidence.PenaltyEvidenceResponse;
import com.example.portalbackend.api.dto.response.penalty_type.PenaltyTypeResponse;
import com.example.portalbackend.api.dto.response.residence.ResidenceResponse;
import com.example.portalbackend.domain.entity.Penalty;
import com.example.portalbackend.util.enumerate.PaidStatus;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public record PenaltyResponse(
    Long id,
    String description,
    String code,
    BigDecimal amount,
    Calendar issueDate,
    Calendar paidDate,
    ResidenceResponse residence,
    PaidStatus status,
    PenaltyTypeResponse type,
    List<PenaltyEvidenceResponse> evidences,
    PaidEvidenceResponse paidEvidence
) {
    public PenaltyResponse(Penalty penalty){
        this(
            penalty.getId(),
            penalty.getDescription(),
            penalty.getCode(),
            penalty.getAmount(),
            penalty.getIssueDate(),
            penalty.getPaidDate(),
            penalty.getResidence() == null ? null :
            new ResidenceResponse(penalty.getResidence()),
            penalty.getStatus(),
            penalty.getType() == null ? null :
            new PenaltyTypeResponse(penalty.getType()),
            penalty.getEvidences() == null ? null :
            penalty.getEvidences().stream().map(PenaltyEvidenceResponse::new).toList(),
            penalty.getPaidEvidence() == null ? null :
            new PaidEvidenceResponse(penalty.getPaidEvidence())
        );
    }
}
