package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.outcome_type.OutcomeTypeData;
import com.example.portalbackend.domain.entity.OutcomeType;

import java.util.List;

public interface IOutcomeTypeService {

    List<OutcomeType> findAll();

    List<OutcomeType> findAllActive();

    OutcomeType findById(Long id);

    OutcomeType save(OutcomeTypeData data);

    OutcomeType update(Long id, OutcomeTypeData data);

    void delete(Long id);

    void activate(Long id);
}
