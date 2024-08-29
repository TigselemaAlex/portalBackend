package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.outcome.OutcomeCreateData;
import com.example.portalbackend.api.dto.request.outcome.OutcomeUpdateData;
import com.example.portalbackend.domain.entity.*;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.example.portalbackend.domain.repository.OutcomeRepository;
import com.example.portalbackend.domain.repository.PaidEvidenceRepository;
import com.example.portalbackend.domain.specifications.OutcomeSpecifications;
import com.example.portalbackend.service.spec.IOutcomeService;
import com.example.portalbackend.service.spec.IOutcomeTypeService;
import com.example.portalbackend.service.spec.IProviderService;
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
public class OutcomeService implements IOutcomeService {

    private final OutcomeRepository outcomeRepository;
    private final IOutcomeTypeService outcomeTypeService;
    private final IProviderService providerService;
    private final PaidEvidenceRepository paidEvidenceRepository;
    private final FileService fileService;

    private final String BUCKET_URL = "https://portal-de-la-vina-bucket.s3.us-east-2.amazonaws.com/";

    @Override
    public Page<Outcome> findAll(OutcomeType type, String code, Provider provider, Calendar from, Calendar to, Pageable pageable) {
        return outcomeRepository.findAll(OutcomeSpecifications.withDynamicFilters(type, code, provider, from, to), pageable);
    }

    @Override
    public Outcome findById(Long id) {
        return outcomeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Outcome save(OutcomeCreateData data) throws IOException, FileUploadException {



        Outcome outcome = Outcome.builder()
                .amount(data.amount())
                .code(generateCode())
                .description(data.description())
                .type(outcomeTypeService.findById(data.type()))
                .paidDate(CalendarUtil.getCalendar(data.paidDate()))
                .provider(providerService.findById(data.provider()))
                .build();
        String fileName = null;
        if (data.paidEvidence() != null && !data.paidEvidence().isEmpty()) {
            fileName = fileService.uploadFile(data.paidEvidence(),generateCode());
            outcome.setPaidEvidence(
                    PaidEvidence.builder()
                            .fileName(fileName)
                            .fileUrl(BUCKET_URL + fileName)
                            .outcome(outcome)
                            .build()
            );
        }
        return outcomeRepository.save(outcome);
    }

    @Override
    public Outcome update(Long id, OutcomeUpdateData data) throws IOException, FileUploadException {
        Outcome outcome = findById(id);
        outcome.setAmount(data.amount());
        outcome.setDescription(data.description());
        outcome.setPaidDate(CalendarUtil.getCalendar(data.paidDate()));
        outcome.setType(outcomeTypeService.findById(data.type()));
        outcome.setProvider(providerService.findById(data.provider()));

        if (data.paidEvidence() != null && !data.paidEvidence().isEmpty()) {
            PaidEvidence paidEvidence = outcome.getPaidEvidence();
            if(Objects.nonNull(paidEvidence)) {
               fileService.delete(paidEvidence.getFileName());
                paidEvidence.setOutcome(null);
                outcome.setPaidEvidence(null);
                paidEvidenceRepository.delete(paidEvidence);
                outcomeRepository.saveAndFlush(outcome);
            }
            String fileName = fileService.uploadFile(data.paidEvidence(), generateCode());
            outcome.setPaidEvidence(
                    PaidEvidence.builder()
                            .fileName(fileName)
                            .fileUrl(BUCKET_URL + fileName)
                            .outcome(outcome)
                            .build()
            );
        }
        return outcomeRepository.save(outcome);
    }

    @Override
    public void delete(Long id) {
        Outcome outcome = findById(id);
        outcomeRepository.delete(outcome);
    }

    private String generateCode(){
        Outcome outcome = outcomeRepository.findFirstByOrderByIdDesc().orElse(null);
        if (outcome == null) {
            return "COD-000001";
        }
        return "COD-" + String.format("%06d", outcome.getId() + 1);
    }
}
