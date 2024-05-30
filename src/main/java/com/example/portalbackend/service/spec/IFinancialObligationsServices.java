package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.response.financial_obligation.FinancialObligationResponse;
import com.example.portalbackend.api.dto.response.financial_obligation.FinancialObligationStatusResponse;
import com.example.portalbackend.domain.entity.Residence;
import com.example.portalbackend.domain.entity.User;

public interface IFinancialObligationsServices {

    FinancialObligationResponse getFinancialObligations(Long user);

    FinancialObligationStatusResponse getFinancialObligationsStatusByUser(User user);
    void getFinancialObligationsStatus();
}
