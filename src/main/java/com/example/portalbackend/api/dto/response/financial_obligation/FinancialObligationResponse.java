package com.example.portalbackend.api.dto.response.financial_obligation;

import com.example.portalbackend.api.dto.response.income.IncomeResponse;
import com.example.portalbackend.api.dto.response.penalty.PenaltyResponse;
import com.example.portalbackend.api.dto.response.residence.ResidenceResponse;
import com.example.portalbackend.domain.entity.Residence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

public record FinancialObligationResponse(

        List<FinancialObligationPerResidence> residencesObligations,
        List<PenaltiesPerResidence> residencesPaidPenalties,
        List<PenaltiesPerResidence> residencesUnpaidPenalties,
        List<PenaltiesUnpaidAmountPerResidence> residencesUnpaidAmounts


) {

    @Getter
    @Service
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class FinancialObligationPerResidence{
        private ResidenceResponse residence;
        private List<IncomeResponse> incomes;
    }

    @Getter
    @Service
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PenaltiesPerResidence{
        private ResidenceResponse residence;
        private List<PenaltyResponse> penalties;
    }

    @Getter
    @Service
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PenaltiesUnpaidAmountPerResidence{
        private ResidenceResponse residence;
        private BigDecimal amount;
    }


}
