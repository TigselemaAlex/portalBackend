package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.guard_incident.GuardIncidentCreateData;
import com.example.portalbackend.api.dto.request.guard_incident.GuardIncidentUpdateData;
import com.example.portalbackend.domain.entity.Guard;
import com.example.portalbackend.domain.entity.GuardIncident;
import com.example.portalbackend.domain.entity.IncidentEvidence;
import com.example.portalbackend.domain.entity.IncidentType;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.example.portalbackend.domain.repository.GuardIncidentRepository;
import com.example.portalbackend.domain.repository.IncidentEvidenceRepository;
import com.example.portalbackend.domain.specifications.GuardIncidentSpecifications;
import com.example.portalbackend.service.spec.IGuardIncidentService;
import com.example.portalbackend.service.spec.IGuardService;
import com.example.portalbackend.service.spec.IIncidentTypeService;
import com.example.portalbackend.util.calendar.CalendarUtil;
import com.example.portalbackend.util.enumerate.GuardIncidentStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class GuardIncidentService implements IGuardIncidentService{

    private final GuardIncidentRepository guardIncidentRepository;
    private final IGuardService guardService;
    private final IIncidentTypeService incidentTypeService;
    private final FileService fileService;
    private final IncidentEvidenceRepository incidentEvidenceRepository;
    private final String BUCKET_URL = "https://portal-de-la-vina-bucket.s3.us-east-2.amazonaws.com/";

    @Override
    public GuardIncident create(GuardIncidentCreateData data) {

        GuardIncident guardIncident = GuardIncident.builder()
                .subject(data.subject())
                .date(CalendarUtil.getCalendar(data.date()))
                .description(data.description())
                .guard(guardService.findById(data.guard()))
                .type(incidentTypeService.getIncidentTypeById(data.type()))
                .status(GuardIncidentStatus.PENDING)
                .build();
        if (Objects.nonNull(data.files())){
            data.files().forEach(file -> {
                IncidentEvidence evidence = null;
                try {
                    evidence = IncidentEvidence.builder()
                            .incident(guardIncident)
                            .build();
                    String fileName = fileService.uploadFile(file);
                    evidence.setFileUrl(BUCKET_URL + fileName);
                    evidence.setFileName(fileName);
                } catch (FileUploadException | IOException e) {
                    throw new RuntimeException(e);
                }
                guardIncident.addEvidence(evidence);
            });
        }
        return guardIncidentRepository.save(guardIncident);
    }

    @Override
    public GuardIncident update(Long id, GuardIncidentUpdateData data) {

        GuardIncident guardIncident = findById(id);
        guardIncident.setSubject(data.subject());
        guardIncident.setDate(CalendarUtil.getCalendar(data.date()));
        guardIncident.setDescription(data.description());
        guardIncident.setGuard(guardService.findById(data.guard()));
        guardIncident.setType(incidentTypeService.getIncidentTypeById(data.type()));
        guardIncident.setStatus(data.status());
        guardIncident.setObservation(data.observation());
        if (Objects.nonNull(data.deletedFiles())){
            data.deletedFiles().forEach(file -> {
                IncidentEvidence evidence = incidentEvidenceRepository.findById(file).orElseThrow(EntityNotFoundException::new);
                fileService.delete(evidence.getFileName());
                incidentEvidenceRepository.delete(evidence);
            });
        }
        if (Objects.nonNull(data.newFiles())){
            data.newFiles().forEach(file -> {
                IncidentEvidence evidence = null;
                try {
                    evidence = IncidentEvidence.builder()
                            .incident(guardIncident)
                            .build();
                    String fileName = fileService.uploadFile(file);
                    evidence.setFileUrl(BUCKET_URL + fileName);
                    evidence.setFileName(fileName);
                } catch (FileUploadException | IOException e) {
                    throw new RuntimeException(e);
                }
                guardIncident.addEvidence(evidence);
            });
        }

        return guardIncidentRepository.save(guardIncident);
    }

    @Override
    public void delete(Long id) {
        GuardIncident guardIncident = findById(id);
        guardIncident.getEvidences().forEach(evidence -> {
            fileService.delete(evidence.getFileName());
        });
        guardIncidentRepository.delete(guardIncident);
    }

    @Override
    @Transactional(readOnly = true)
    public GuardIncident findById(Long id) {
        return guardIncidentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Page<GuardIncident> findAll(String subject, Long date, Long guard, Long type, GuardIncidentStatus status, Pageable pageable) {
        Guard guardEntity = null == guard ? null : guardService.findById(guard);
        IncidentType incidentType = null == type ? null : incidentTypeService.getIncidentTypeById(type);
        Calendar dateCalendar = null == date ? null : CalendarUtil.getCalendar(date);
        return guardIncidentRepository.findAll(GuardIncidentSpecifications.withDynamicFilters(subject, dateCalendar, guardEntity, incidentType, status), pageable);
    }


}
