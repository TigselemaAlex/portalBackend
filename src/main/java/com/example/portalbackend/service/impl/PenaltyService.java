package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.penalty.PenaltyCreateData;
import com.example.portalbackend.api.dto.request.penalty.PenaltyUpdateData;
import com.example.portalbackend.domain.entity.*;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.example.portalbackend.domain.repository.PaidEvidenceRepository;
import com.example.portalbackend.domain.repository.PenaltyEvidenceRepository;
import com.example.portalbackend.domain.repository.PenaltyRepository;
import com.example.portalbackend.domain.specifications.PenaltySpecifications;
import com.example.portalbackend.service.spec.IPenaltyService;
import com.example.portalbackend.service.spec.IPenaltyTypeService;
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
public class PenaltyService implements IPenaltyService {

    private final PenaltyRepository penaltyRepository;
    private final IPenaltyTypeService penaltyTypeService;
    private final IResidenceService residenceService;
    private final PaidEvidenceRepository paidEvidenceRepository;
    private final PenaltyEvidenceRepository penaltyEvidenceRepository;
    private final FileService fileService;
    private final String BUCKET_URL = "https://portal-de-la-vina-bucket.s3.us-east-2.amazonaws.com/";

    @Override
    public Page<Penalty> findAllActive(PenaltyType type, Calendar from, Calendar to, Residence residence, PaidStatus status, Pageable pageable) {
        return penaltyRepository.findAll(PenaltySpecifications.withDynamicFilters(type, from, to, residence, status), pageable);
    }

    @Override
    public Penalty findById(Long id) {
        return penaltyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Penalty save(PenaltyCreateData data) throws IOException, FileUploadException {
        PenaltyType type = penaltyTypeService.findById(data.type());
        Penalty penalty = Penalty.builder()
                .amount(type.getPrice())
                .description(data.description())
                .type(type)
                .residence(residenceService.findById(data.residence()))
                .code(generateCode())
                .issueDate(CalendarUtil.getCalendar(data.issueDate()))
                .paidDate(CalendarUtil.getCalendar(data.paidDate()))
                .status(data.status())
                .build();
        String fileName = null;
        if (!Objects.equals(data.amount(), type.getPrice())) {
            penalty.setAmount(data.amount());
        }
        if (data.paidEvidence() != null && data.paidEvidence().getSize() > 0){
            fileName = fileService.uploadFile(data.paidEvidence(), generateCode());
            penalty.setPaidEvidence(PaidEvidence.builder()
                    .fileName(fileName)
                    .fileUrl(BUCKET_URL + fileName)
                    .penalty(penalty)
                    .build());
        }

        if (Objects.nonNull(data.files()) && !data.files().isEmpty()){
            data.files().forEach(file -> {
                PenaltyEvidence evidence = null;
                try {
                    evidence = PenaltyEvidence.builder()
                            .penalty(penalty)
                            .build();
                    String fileEvidenceName = fileService.uploadFile(file, generateCode());
                    evidence.setFileUrl(BUCKET_URL + fileEvidenceName);
                    evidence.setFileName(fileEvidenceName);
                } catch (FileUploadException | IOException e) {
                    throw new RuntimeException(e);
                }
                penalty.addEvidence(evidence);
            });
        }
        return penaltyRepository.save(penalty);
    }

    @Override
    public Penalty update(Long id, PenaltyUpdateData data) throws IOException, FileUploadException {
        PenaltyType type = penaltyTypeService.findById(data.type());
        Penalty penalty = findById(id);
        penalty.setAmount(type.getPrice());
        penalty.setDescription(data.description());
        penalty.setType(type);
        penalty.setResidence(residenceService.findById(data.residence()));
        penalty.setIssueDate(CalendarUtil.getCalendar(data.issueDate()));
        penalty.setPaidDate(CalendarUtil.getCalendar(data.paidDate()));
        penalty.setStatus(data.status());

        if(data.status().equals(PaidStatus.UNPAID)){
            penalty.setPaidDate(null);
            PaidEvidence paidEvidence = penalty.getPaidEvidence();
            if(Objects.nonNull(paidEvidence)){
                fileService.delete(paidEvidence.getFileName());
                paidEvidence.setPenalty(null);
                penalty.setPaidEvidence(null);
                paidEvidenceRepository.delete(paidEvidence);
                penaltyRepository.saveAndFlush(penalty);
            }
        }else{
            if (data.paidEvidence() != null && data.paidEvidence().getSize() > 0){
                PaidEvidence paidEvidence = penalty.getPaidEvidence();
                if(Objects.nonNull(paidEvidence)){
                    fileService.delete(paidEvidence.getFileName());
                    paidEvidence.setPenalty(null);
                    penalty.setPaidEvidence(null);
                    paidEvidenceRepository.delete(paidEvidence);
                    penaltyRepository.saveAndFlush(penalty);
                }
                String fileName = fileService.uploadFile(data.paidEvidence(), generateCode());
                penalty.setPaidEvidence(PaidEvidence.builder()
                        .fileName(fileName)
                        .fileUrl(BUCKET_URL + fileName)
                        .penalty(penalty)
                        .build());
            }
        }


        if(Objects.nonNull(data.filesToDelete())){
            data.filesToDelete().forEach(file -> {
                PenaltyEvidence evidence = penaltyEvidenceRepository.findById(file).orElseThrow(EntityNotFoundException::new);
                fileService.delete(evidence.getFileName());
                penaltyEvidenceRepository.delete(evidence);
            });
        }

        if(Objects.nonNull(data.files())){
            data.files().forEach(file -> {
                PenaltyEvidence evidence = null;
                try {
                    evidence = PenaltyEvidence.builder()
                            .penalty(penalty)
                            .build();
                    String fileEvidenceName = fileService.uploadFile(file, generateCode());
                    evidence.setFileUrl(BUCKET_URL + fileEvidenceName);
                    evidence.setFileName(fileEvidenceName);
                } catch (FileUploadException | IOException e) {
                    throw new RuntimeException(e);
                }
                penalty.addEvidence(evidence);
            });
        }

        return penaltyRepository.save(penalty);
    }

    @Override
    public void deleteById(Long id) {
        Penalty penalty = findById(id);
        penaltyRepository.delete(penalty);

    }

    private String generateCode(){
        Penalty penalty = penaltyRepository.findFirstByOrderByIdDesc().orElse(null);
        if (penalty == null) {
            return "MLT-000001";
        }
        return "MLT-" + String.format("%06d", penalty.getId() + 1);
    }
}
