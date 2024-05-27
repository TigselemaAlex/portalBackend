package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.penalty_type.PenaltyTypeCreateData;
import com.example.portalbackend.api.dto.request.penalty_type.PenaltyTypeUpdateData;
import com.example.portalbackend.domain.entity.PenaltyType;
import com.example.portalbackend.domain.repository.PenaltyTypeRepository;
import com.example.portalbackend.service.spec.IPenaltyTypeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PenaltyTypeService implements IPenaltyTypeService {

    private final PenaltyTypeRepository penaltyTypeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PenaltyType> findAllActive() {
        return penaltyTypeRepository.findAllByActiveIsTrueOrderById();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PenaltyType> findAll() {
        return penaltyTypeRepository.findAllByOrderById();
    }

    @Override
    @Transactional(readOnly = true)
    public PenaltyType findById(Long id) {
        return penaltyTypeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public PenaltyType save(PenaltyTypeCreateData data) {
        return penaltyTypeRepository.save(
                PenaltyType.builder()
                        .name(data.name())
                        .description(data.description())
                        .canBeDeleted(true)
                        .price(data.price())
                        .build()
        );
    }

    @Override
    public PenaltyType update(Long id, PenaltyTypeUpdateData data) {
        PenaltyType penaltyType = findById(id);
        penaltyType.setName(data.name());
        penaltyType.setDescription(data.description());
        penaltyType.setPrice(data.price());
        return penaltyTypeRepository.save(penaltyType);
    }

    @Override
    public void deleteById(Long id) {
        PenaltyType penaltyType = findById(id);
        penaltyTypeRepository.delete(penaltyType);
    }

    @Override
    public void reactivateById(Long id) {
        PenaltyType penaltyType = findById(id);
        penaltyType.setActive(true);
        penaltyTypeRepository.save(penaltyType);
    }
}
