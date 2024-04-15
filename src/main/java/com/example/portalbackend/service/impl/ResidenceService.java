package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.residence.ResidenceCreateData;
import com.example.portalbackend.api.dto.request.residence.ResidenceUpdateData;
import com.example.portalbackend.domain.entity.Passage;
import com.example.portalbackend.domain.entity.Residence;
import com.example.portalbackend.domain.entity.User;
import com.example.portalbackend.domain.repository.ResidenceRepository;
import com.example.portalbackend.service.spec.IPassageService;
import com.example.portalbackend.service.spec.IResidenceService;
import com.example.portalbackend.service.spec.IUserService;
import com.example.portalbackend.util.number.NumberUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ResidenceService implements IResidenceService{

    private final ResidenceRepository residenceRepository;
    private final IUserService userService;
    private final IPassageService passageService;

    @Override
    public Residence update(ResidenceUpdateData residence, Long id) {
        Residence residenceToUpdate = findById(id);
        if (Objects.nonNull(residence.user())) {
            User user = userService.findById(residence.user());
            residenceToUpdate.setUser(user);
        }else{
            residenceToUpdate.setUser(null);
        }
        return residenceRepository.save(residenceToUpdate);
    }

    @Override
    public Residence save(ResidenceCreateData residence) {
        Passage passage = passageService.findById(residence.passage());
        Residence residenceToSave = Residence.builder()
                .number(NumberUtils.formatToThreeDigits(residence.number()))
                .passage(passage)
                .build();
        if (Objects.nonNull(residence.user())) {
            User user = userService.findById(residence.user());
            residenceToSave.setUser(user);
        }
        return residenceRepository.save(residenceToSave);
    }

    @Override
    @Transactional(readOnly = true)
    public Residence findById(Long id) {
        return residenceRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Residence> findAll(String number, Pageable pageable) {
        return residenceRepository.findAllByNumberContainingIgnoreCaseOrUserNamesContainingIgnoreCaseOrUserSurnamesContainingIgnoreCase(number, number,number,pageable);
    }
}
