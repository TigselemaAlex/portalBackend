package com.example.portalbackend.api.dto.response.income;

import com.example.portalbackend.api.dto.response.income_type.IncomeTypeResponse;
import com.example.portalbackend.api.dto.response.paid_evidence.PaidEvidenceResponse;
import com.example.portalbackend.api.dto.response.parking.ParkingResponse;
import com.example.portalbackend.api.dto.response.residence.ResidenceResponse;
import com.example.portalbackend.domain.entity.Income;

import java.math.BigDecimal;
import java.util.Calendar;

public record IncomeResponse(
        Long id,
        String description,
        String code,
        BigDecimal amount,
        Calendar paidDate,
        Integer monthsPaid,
        Calendar paidSince,
        Calendar paidUntil,
        IncomeTypeResponse type,
        ResidenceResponse residence,
        ParkingResponse parking,
        PaidEvidenceResponse paidEvidence,
        Boolean canBeDeleted
) {
    public IncomeResponse(Income income){
        this(
                income.getId(),
                income.getDescription(),
                income.getCode(),
                income.getAmount(),
                income.getPaidDate(),
                income.getMonthsPaid(),
                income.getPaidSince(),
                income.getPaidUntil(),
                income.getType() == null ? null :
                new IncomeTypeResponse(income.getType()),
                income.getResidence() == null ? null :
                new ResidenceResponse(income.getResidence()),
                income.getParking() == null ? null :
                new ParkingResponse(income.getParking()),
                income.getPaidEvidence() == null ? null :
                new PaidEvidenceResponse(income.getPaidEvidence()),
                income.getCanBeDeleted()
        );
    }
}
