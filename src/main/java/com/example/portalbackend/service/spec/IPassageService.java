package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.passage.PassageCreateData;
import com.example.portalbackend.api.dto.request.passage.PassageUpdateData;
import com.example.portalbackend.domain.entity.Passage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPassageService {
    Passage create(PassageCreateData passage);
    Page<Passage> findAll(String name, Pageable pageable);
    List<Passage> findAllActive();
    Passage findById(Long id);
    Passage update(Long id, PassageUpdateData passage);
    void delete(Long id);
}
