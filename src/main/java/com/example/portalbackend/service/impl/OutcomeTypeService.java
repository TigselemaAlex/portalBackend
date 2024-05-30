package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.outcome_type.OutcomeTypeData;
import com.example.portalbackend.domain.entity.OutcomeType;
import com.example.portalbackend.domain.repository.OutcomeTypeRepository;
import com.example.portalbackend.service.spec.IOutcomeTypeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OutcomeTypeService implements IOutcomeTypeService {

    private final OutcomeTypeRepository outcomeTypeRepository;
    @Override
    @Transactional(readOnly = true)
    public List<OutcomeType> findAll() {
        return outcomeTypeRepository.findAllByOrderById();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OutcomeType> findAllActive() {
        return outcomeTypeRepository.findAllByActiveIsTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public OutcomeType findById(Long id) {
        return outcomeTypeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public OutcomeType save(OutcomeTypeData data) {
        return outcomeTypeRepository.save(OutcomeType.builder()
                .name(data.name())
                .description(data.description())
                .build());
    }

    @Override
    public OutcomeType update(Long id, OutcomeTypeData data) {

        OutcomeType outcomeType = findById(id);
        outcomeType.setName(data.name());
        outcomeType.setDescription(data.description());
        return outcomeTypeRepository.save(outcomeType);
    }

    @Override
    public void delete(Long id) {
        OutcomeType outcomeType = findById(id);
        outcomeTypeRepository.delete(outcomeType);
    }

    @Override
    public void activate(Long id) {
        OutcomeType outcomeType = findById(id);
        outcomeType.setActive(true);
        outcomeTypeRepository.save(outcomeType);
    }
}
