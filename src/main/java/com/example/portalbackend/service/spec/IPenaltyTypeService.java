package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.penalty_type.PenaltyTypeCreateData;
import com.example.portalbackend.api.dto.request.penalty_type.PenaltyTypeUpdateData;
import com.example.portalbackend.domain.entity.PenaltyType;

import java.util.List;

public interface IPenaltyTypeService {

    List<PenaltyType> findAllActive();

    List<PenaltyType> findAll();

    PenaltyType findById(Long id);

    PenaltyType save(PenaltyTypeCreateData data);

    PenaltyType update(Long id, PenaltyTypeUpdateData data);

    void deleteById(Long id);

    void reactivateById(Long id);
}
