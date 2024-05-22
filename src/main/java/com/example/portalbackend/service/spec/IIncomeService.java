package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.income.IncomeFeesCreateData;
import com.example.portalbackend.api.dto.request.income.IncomeFeesUpdateData;
import com.example.portalbackend.domain.entity.Income;
import com.example.portalbackend.domain.entity.IncomeType;
import com.example.portalbackend.domain.entity.Residence;
import com.example.portalbackend.domain.exception.FileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Calendar;

public interface IIncomeService {

    Page<Income> getIncomesByFiltersMonthly(IncomeType type, Calendar from, Calendar to, Residence residence, Pageable pageable);
    Page<Income> getIncomesByFiltersCasual(IncomeType type, Calendar from, Calendar to, Residence residence, Pageable pageable);

    Income getIncomeById(Long id);

    Income saveIncomeFees(IncomeFeesCreateData data) throws IOException, FileUploadException;

    Income updateIncomeFees(Long id, IncomeFeesUpdateData data) throws IOException, FileUploadException;

    void deleteIncome(Long id);
}
