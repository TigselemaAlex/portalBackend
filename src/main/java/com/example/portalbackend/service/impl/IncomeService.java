package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.income.IncomeCasualCreateData;
import com.example.portalbackend.api.dto.request.income.IncomeCasualUpdateData;
import com.example.portalbackend.api.dto.request.income.IncomeFeesCreateData;
import com.example.portalbackend.api.dto.request.income.IncomeFeesUpdateData;
import com.example.portalbackend.domain.entity.*;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.example.portalbackend.domain.repository.IncomeRepository;
import com.example.portalbackend.domain.repository.PaidEvidenceRepository;
import com.example.portalbackend.domain.specifications.IncomeSpecifications;
import com.example.portalbackend.service.spec.IIncomeService;
import com.example.portalbackend.service.spec.IIncomeTypeService;
import com.example.portalbackend.service.spec.IParkingService;
import com.example.portalbackend.service.spec.IResidenceService;
import com.example.portalbackend.util.calendar.CalendarUtil;
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
    private final IParkingService parkingService;
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
        Residence residence = residenceService.findById(data.residence());
        IncomeType incomeType = incomeTypeService.findById(data.incomeType());
        Parking parking = null;
        if (Objects.nonNull(data.parking())) {
            parking = parkingService.findByIdAndResidence(data.parking(), residence);
        }

        Income income = Income.builder()
                .description(data.description())
                .paidDate(CalendarUtil.getCalendar(data.paidDate()))
                .amount(incomeType.getPrice())
                .paidSince(CalendarUtil.getCalendarWithoutDays(data.paidSince()))
                .paidUntil(CalendarUtil.getCalendarWithoutDays(data.paidUntil()))
                .residence(residence)
                .parking(parking)
                .code(generateCode())
                .canBeDeleted(true)
                .type(incomeType)
                .monthsPaid(CalendarUtil.getMonthDifference(CalendarUtil.getCalendar(data.paidSince()), CalendarUtil.getCalendar(data.paidUntil())))
                .build();
        String fileName = null;
        if (!Objects.equals(data.amount(), incomeType.getPrice())) {
            income.setAmount(data.amount());
        }
        if (data.paidEvidence() != null && data.paidEvidence().getSize() > 0) {
            fileName = fileService.uploadFile(data.paidEvidence(), generateCode());
            income.setPaidEvidence(PaidEvidence.builder()
                    .fileName(fileName)
                    .fileUrl(BUCKET_URL + fileName)
                    .income(income)
                    .build());
        }
        return incomeRepository.save(income);
    }

    @Override
    public Income saveIncomeCasual(IncomeCasualCreateData data) throws IOException, FileUploadException {
        IncomeType incomeType = incomeTypeService.findById(data.incomeType());

        Income income = Income.builder()
                .description(data.description())
                .paidDate(CalendarUtil.getCalendar(data.paidDate()))
                .amount(incomeType.getPrice())
                .residence(residenceService.findById(data.residence()))
                .code(generateCode())
                .canBeDeleted(true)
                .type(incomeType)
                .build();
        String fileName = null;
        if (!Objects.equals(data.amount(), incomeType.getPrice())) {
            income.setAmount(data.amount());
        }
        if (data.paidEvidence() != null && data.paidEvidence().getSize() > 0) {
            fileName = fileService.uploadFile(data.paidEvidence(), generateCode());
            income.setPaidEvidence(PaidEvidence.builder()
                    .fileName(fileName)
                    .fileUrl(BUCKET_URL + fileName)
                    .income(income)
                    .build());
        }
        return incomeRepository.save(income);
    }

    @Override
    public Income updateIncomeCasual(Long id, IncomeCasualUpdateData data) throws IOException, FileUploadException {
        IncomeType incomeType = incomeTypeService.findById(data.incomeType());
        Income income = getIncomeById(id);
        income.setDescription(data.description());
        income.setAmount(data.amount());
        income.setResidence(residenceService.findById(data.residence()));
        income.setType(incomeType);
        if (data.paidEvidence() != null && data.paidEvidence().getSize() > 0) {
            PaidEvidence paidEvidence = income.getPaidEvidence();
            if (Objects.nonNull(paidEvidence)) {
                fileService.delete(paidEvidence.getFileName());
                paidEvidence.setIncome(null);
                income.setPaidEvidence(null);
                paidEvidenceRepository.delete(paidEvidence);
                incomeRepository.saveAndFlush(income);
            }
            String fileName = fileService.uploadFile(data.paidEvidence(), income.getCode());
            income.setPaidEvidence(PaidEvidence.builder()
                    .fileName(fileName)
                    .fileUrl(BUCKET_URL + fileName)
                    .income(income)
                    .build());

        }
        return incomeRepository.saveAndFlush(income);
    }

    @Override
    public Income updateIncomeFees(Long id, IncomeFeesUpdateData data) throws IOException, FileUploadException {
        IncomeType incomeType = incomeTypeService.findById(data.incomeType());
        Residence residence = residenceService.findById(data.residence());
        Parking parking = null;
        if (Objects.nonNull(data.parking())) {
            parking = parkingService.findByIdAndResidence(data.parking(), residence);
        }

        Income income = getIncomeById(id);
        income.setDescription(data.description());
        income.setAmount(data.amount());
        income.setPaidSince(CalendarUtil.getCalendarWithoutDays(data.paidSince()));
        income.setPaidUntil(CalendarUtil.getCalendarWithoutDays(data.paidUntil()));
        income.setResidence(residence);
        income.setType(incomeType);
        income.setMonthsPaid(CalendarUtil.getMonthDifference(CalendarUtil.getCalendar(data.paidSince()), CalendarUtil.getCalendar(data.paidUntil())));
        if (data.paidEvidence() != null && data.paidEvidence().getSize() > 0) {
            PaidEvidence paidEvidence = income.getPaidEvidence();
            if (Objects.nonNull(paidEvidence)) {
                fileService.delete(paidEvidence.getFileName());
                paidEvidence.setIncome(null);
                income.setPaidEvidence(null);
                paidEvidenceRepository.delete(paidEvidence);
                incomeRepository.saveAndFlush(income);
            }
            String fileName = fileService.uploadFile(data.paidEvidence(), income.getCode());
            income.setPaidEvidence(PaidEvidence.builder()
                    .fileName(fileName)
                    .fileUrl(BUCKET_URL + fileName)
                    .income(income)
                    .build());

        }
        return incomeRepository.saveAndFlush(income);
    }

    @Override
    public void deleteIncome(Long id) {
        Income income = getIncomeById(id);
        incomeRepository.delete(income);
    }

    @Override
    public Income getLastByResidenceAndTypeAndParking(Long residence, Long incomeType, Long parking) {
        Residence residenceObj = residenceService.findById(residence);
        IncomeType incomeTypeObj = incomeTypeService.findById(incomeType);
        if (parking != null) {
            Parking parkingObj = parkingService.findByIdAndResidence(parking, residenceObj);
            return incomeRepository.findFirstByActiveIsTrueAndResidenceAndTypeAndParkingOrderByPaidUntilDesc(residenceObj, incomeTypeObj, parkingObj)
                    .orElse(null);
        }
        return incomeRepository.findFirstByActiveIsTrueAndResidenceAndTypeOrderByPaidUntilDesc(residenceObj, incomeTypeObj)
                .orElse(null);
    }

    private String generateCode() {
        Income income = incomeRepository.findFirstByOrderByIdDesc().orElse(null);
        if (income == null) {
            return "COD-000001";
        }
        return "COD-" + String.format("%06d", income.getId() + 1);
    }
}
