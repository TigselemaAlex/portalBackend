package com.example.portalbackend.api.dto.response.financial_obligation;

import com.example.portalbackend.api.dto.response.residence.ResidenceResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

public record FinancialObligationStatusResponse(
        List<FinancialStatusDetail> financialStatusDetails
) {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class FinancialStatusDetail{
        private ResidenceResponse residence;
        List<FinancialStatusDetailComplement> complement;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class FinancialStatusDetailComplement{
        private String type;
        private Integer totalMonthsDelayed;
        private List<FinancialStatusComplementParkingData> parkingData;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class FinancialStatusComplementParkingData{
        private String code;
        private Integer totalMonthsDelayed;
    }

}
