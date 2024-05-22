package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.income.IncomeFeesCreateData;
import com.example.portalbackend.api.dto.request.income.IncomeFeesUpdateData;
import com.example.portalbackend.domain.entity.Income;
import com.example.portalbackend.domain.entity.IncomeType;
import com.example.portalbackend.domain.entity.PaidEvidence;
import com.example.portalbackend.domain.entity.Residence;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.example.portalbackend.domain.repository.IncomeRepository;
import com.example.portalbackend.domain.repository.PaidEvidenceRepository;
import com.example.portalbackend.domain.specifications.IncomeSpecifications;
import com.example.portalbackend.service.spec.IIncomeService;
import com.example.portalbackend.service.spec.IIncomeTypeService;
import com.example.portalbackend.service.spec.IResidenceService;
import com.example.portalbackend.util.calendar.CalendarUtil;
import com.example.portalbackend.util.enumerate.PaidStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class IncomeService implements IIncomeService {

    private final IncomeRepository incomeRepository;
    private final IResidenceService residenceService;
    private final IIncomeTypeService incomeTypeService;
    private final PaidEvidenceRepository paidEvidenceRepository;
    private final FileService fileService;

    private final String BUCKET_URL = "https://portal-de-la-vina-bucket.s3.us-east-2.amazonaws.com/";

    @Override
    @Transactional(readOnly = true)
    public Page<Income> getIncomesByFiltersMonthly(IncomeType type, Calendar from, Calendar to, Residence residence, Pageable pageable) {
        return incomeRepository.findAll(IncomeSpecifications.withDynamicFiltersMonthly(
                type, from, to, residence), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Income> getIncomesByFiltersCasual(IncomeType type, Calendar from, Calendar to, Residence residence, Pageable pageable) {
        return incomeRepository.findAll(IncomeSpecifications.withDynamicFiltersCasual(
                type, from, to, residence), pageable);
    }

    @Override
    public Income getIncomeById(Long id) {
        return incomeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Income saveIncomeFees(IncomeFeesCreateData data) throws IOException, FileUploadException {

        IncomeType incomeType = incomeTypeService.findById(data.incomeType());
        Income income = Income.builder()
                .description(data.description())
                .paidDate(CalendarUtil.getCalendar(data.paidDate()))
                .amount(incomeType.getPrice())
                .paidSince(CalendarUtil.getCalendarWithoutDays(data.paidSince()))
                .paidUntil(CalendarUtil.getCalendarWithoutDays(data.paidUntil()))
                .residence(residenceService.findById(data.residence()))
                .type(incomeType)
                .monthsPaid(CalendarUtil.getMonthDifference(CalendarUtil.getCalendar(data.paidSince()), CalendarUtil.getCalendar(data.paidUntil())))
                .build();
        String fileName = null;
        if (!Objects.equals(data.amount(), incomeType.getPrice())) {
            income.setAmount(data.amount());
        }
        if (data.paidEvidence() != null && data.paidEvidence().getSize() > 0) {
            fileName = fileService.uploadFile(data.paidEvidence());
            income.setPaidEvidence(PaidEvidence.builder()
                    .fileName(fileName)
                    .fileUrl(BUCKET_URL + fileName)
                    .income(income)
                    .build());
        }
        return incomeRepository.save(income);
    }

    @Override
    public Income updateIncomeFees(Long id, IncomeFeesUpdateData data) throws IOException, FileUploadException {
        IncomeType incomeType = incomeTypeService.findById(data.incomeType());
        Income income = getIncomeById(id);
        income.setDescription(data.description());
        income.setAmount(data.amount());
        income.setPaidSince(CalendarUtil.getCalendarWithoutDays(data.paidSince()));
        income.setPaidUntil(CalendarUtil.getCalendarWithoutDays(data.paidUntil()));
        income.setResidence(residenceService.findById(data.residence()));
        income.setType(incomeType);
        income.setMonthsPaid(CalendarUtil.getMonthDifference(CalendarUtil.getCalendar(data.paidSince()), CalendarUtil.getCalendar(data.paidUntil())));
        if (data.paidEvidence() != null && data.paidEvidence().getSize() > 0) {
            PaidEvidence paidEvidence = income.getPaidEvidence();
            if (Objects.nonNull(paidEvidence)) {
                fileService.delete(paidEvidence.getFileName());
                paidEvidenceRepository.delete(paidEvidence);
            }
            String fileName = fileService.uploadFile(data.paidEvidence());
            income.setPaidEvidence(PaidEvidence.builder()
                    .fileName(fileName)
                    .fileUrl(BUCKET_URL + fileName)
                    .income(income)
                    .build());

        }
        return incomeRepository.save(income);
    }

    @Override
    public void deleteIncome(Long id) {
        Income income = getIncomeById(id);
        incomeRepository.delete(income);
    }
}
