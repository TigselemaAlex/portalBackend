package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.passage.PassageCreateData;
import com.example.portalbackend.api.dto.request.passage.PassageUpdateData;
import com.example.portalbackend.domain.entity.Passage;
import com.example.portalbackend.domain.repository.PassageRepository;
import com.example.portalbackend.service.spec.IPassageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PassageService implements IPassageService {

    private final PassageRepository passageRepository;

    public PassageService(PassageRepository passageRepository) {
        this.passageRepository = passageRepository;
    }

    @Override
    public Passage create(PassageCreateData passage) {
        return passageRepository.save(Passage.builder()
                .name(passage.name())
                .build());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Passage> findAll(String name, Pageable pageable) {
        return passageRepository.findAllByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public List<Passage> findAllActive() {
        return passageRepository.findAllByActiveIsTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Passage findById(Long id) {
        return passageRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Passage update(Long id, PassageUpdateData passage) {
        Passage passageToUpdate = findById(id);
        passageToUpdate.setName(passage.name());
        passageToUpdate.setActive(passage.active());
        return passageRepository.save(passageToUpdate);
    }

    @Override
    public void delete(Long id) {
        Passage passage = findById(id);
        passageRepository.delete(passage);

    }

    public Passage findByName(String name) {
        return passageRepository.findByName(name);
    }
}
