package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.response.financial_obligation.FinancialObligationResponse;
import com.example.portalbackend.api.dto.response.financial_obligation.FinancialObligationStatusResponse;
import com.example.portalbackend.api.dto.response.income.IncomeResponse;
import com.example.portalbackend.domain.entity.Residence;
import com.example.portalbackend.domain.entity.User;

import java.util.List;

public interface IFinancialObligationsServices {

    FinancialObligationResponse getFinancialObligations(Long user);

    FinancialObligationStatusResponse getFinancialObligationsStatusByUser(User user);
    void getFinancialObligationsStatus();

    FinancialObligationStatusResponse getFinancialObligationsStatusByResidence(Residence residence);

    List<IncomeResponse> getBlueParkingReport();

    List<IncomeResponse> getAliquotsReport();
}
