package com.example.portalbackend.api.dto.response.dashboard;

import com.example.portalbackend.api.dto.response.income.IncomeResponse;
import com.example.portalbackend.api.dto.response.outcome.OutcomeResponse;
import com.example.portalbackend.api.dto.response.penalty.PenaltyResponse;

import java.math.BigDecimal;
import java.util.List;

public record TreasureDashboardResponse(
        BigDecimal totalIncomes,
        BigDecimal totalPenalties,
        BigDecimal totalOutcomes,
        BigDecimal totalTreasure,
        BigDecimal totalIncomesThisMonth,
        BigDecimal totalPenaltiesThisMonth,
        BigDecimal totalOutcomesThisMonth,
        BigDecimal totalTreasureThisMonth,
        List<IncomeResponse> lastIncomes,
        List<PenaltyResponse> lastPenalties,
        List<OutcomeResponse> lastOutcomes,
        BigDecimal totalIncomesLast,
        BigDecimal totalPenaltiesLast,
        BigDecimal  totalOutcomesLast,
        BigDecimal totalTreasureLast
) {
}
