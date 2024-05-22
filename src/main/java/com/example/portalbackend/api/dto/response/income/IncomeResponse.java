package com.example.portalbackend.api.dto.response.income;

import com.example.portalbackend.api.dto.response.income_type.IncomeTypeResponse;
import com.example.portalbackend.api.dto.response.paid_evidence.PaidEvidenceResponse;
import com.example.portalbackend.api.dto.response.residence.ResidenceResponse;
import com.example.portalbackend.domain.entity.Income;

import java.util.Calendar;

public record IncomeResponse(
        Long id,
        String description,
        String amount,
        Calendar paidDate,
        Integer monthsPaid,
        Calendar paidSince,
        Calendar paidUntil,
        IncomeTypeResponse type,
        ResidenceResponse residence,
        PaidEvidenceResponse paidEvidence
) {
    public IncomeResponse(Income income){
        this(
                income.getId(),
                income.getDescription(),
                income.getAmount().toString(),
                income.getPaidDate(),
                income.getMonthsPaid(),
                income.getPaidSince(),
                income.getPaidUntil(),
                income.getType() == null ? null :
                new IncomeTypeResponse(income.getType()),
                income.getResidence() == null ? null :
                new ResidenceResponse(income.getResidence()),
                income.getPaidEvidence() == null ? null :
                new PaidEvidenceResponse(income.getPaidEvidence())
        );
    }
}
