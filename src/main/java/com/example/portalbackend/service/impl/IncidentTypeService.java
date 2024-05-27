package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.incident_type.IncidentTypeCreateData;
import com.example.portalbackend.api.dto.request.incident_type.IncidentTypeUpdateData;
import com.example.portalbackend.domain.entity.IncidentType;
import com.example.portalbackend.domain.repository.IncidentTypeRepository;
import com.example.portalbackend.service.spec.IIncidentTypeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class IncidentTypeService implements IIncidentTypeService {

    private final IncidentTypeRepository incidentTypeRepository;

    @Override
    public List<IncidentType> getAllIncidentTypes() {
        return incidentTypeRepository.findAllByOrderByCreatedAt();
    }

    @Override
    public List<IncidentType> getActiveIncidentTypes() {
        return incidentTypeRepository.findAllByActiveIsTrueOrderByCreatedAt();
    }

    @Override
    public IncidentType getIncidentTypeById(Long id) {
        return incidentTypeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public IncidentType addIncidentType(IncidentTypeCreateData data) {
        return incidentTypeRepository.save(IncidentType.builder()
                .name(data.name())
                .description(data.description())
                .severity(data.severity())
                .build());
    }

    @Override
    public IncidentType updateIncidentType(Long id, IncidentTypeUpdateData data) {

        IncidentType incidentType = getIncidentTypeById(id);
        incidentType.setName(data.name());
        incidentType.setDescription(data.description());
        incidentType.setSeverity(data.severity());
        incidentType.setActive(data.active());

        return incidentTypeRepository.save(incidentType);
    }

    @Override
    public void deleteIncidentType(Long id) {
        IncidentType incidentType = getIncidentTypeById(id);
        incidentTypeRepository.delete(incidentType);
    }

    @Override
    public void reactivateIncidentType(Long id) {
        IncidentType incidentType = getIncidentTypeById(id);
        incidentType.setActive(true);
        incidentTypeRepository.save(incidentType);
    }

}
