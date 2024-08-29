package com.example.portalbackend.api.dto.response.outcome;

import com.example.portalbackend.api.dto.response.outcome_type.OutcomeTypeResponse;
import com.example.portalbackend.api.dto.response.paid_evidence.PaidEvidenceResponse;
import com.example.portalbackend.api.dto.response.provider.ProviderResponse;
import com.example.portalbackend.domain.entity.Outcome;

import java.math.BigDecimal;
import java.util.Calendar;

public record OutcomeResponse(
        Long id,
        String description,
        String code,
        BigDecimal amount,
        Calendar paidDate,
        ProviderResponse provider,
        OutcomeTypeResponse type,
        PaidEvidenceResponse paidEvidence
) {
    public OutcomeResponse(Outcome outcome){
        this(
                outcome.getId(),
                outcome.getDescription(),
                outcome.getCode(),
                outcome.getAmount(),
                outcome.getPaidDate(),
                outcome.getProvider() == null ? null :
                new ProviderResponse(outcome.getProvider()),
                outcome.getType() == null ? null :
                new OutcomeTypeResponse(outcome.getType()),
                outcome.getPaidEvidence() == null ? null :
                new PaidEvidenceResponse(outcome.getPaidEvidence())
        );
    }
}
