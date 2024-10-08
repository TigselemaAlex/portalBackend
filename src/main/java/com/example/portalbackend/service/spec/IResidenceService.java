package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.residence.ResidenceCreateData;
import com.example.portalbackend.api.dto.request.residence.ResidenceUpdateData;
import com.example.portalbackend.domain.entity.Residence;
import com.example.portalbackend.domain.entity.ResidenceHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IResidenceService {
    Residence update(ResidenceUpdateData residence, Long id);
    Residence save(ResidenceCreateData residence);
    Residence findById(Long id);
    Page<Residence> findAll(String number, Pageable pageable);
    List<Residence> findAllNumbers();
    List<ResidenceHistory> findResidenceHistory(Long id);
}
