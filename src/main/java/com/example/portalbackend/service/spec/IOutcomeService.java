package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.outcome.OutcomeCreateData;
import com.example.portalbackend.api.dto.request.outcome.OutcomeUpdateData;
import com.example.portalbackend.domain.entity.Outcome;
import com.example.portalbackend.domain.entity.OutcomeType;
import com.example.portalbackend.domain.entity.Provider;
import com.example.portalbackend.domain.exception.FileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Calendar;

public interface IOutcomeService {

    Page<Outcome> findAll(OutcomeType type, String code, Provider provider, Calendar from , Calendar to, Pageable pageable);

    Outcome findById(Long id);

    Outcome save(OutcomeCreateData data) throws IOException, FileUploadException;

    Outcome update(Long id, OutcomeUpdateData data) throws IOException, FileUploadException;

    void delete(Long id);

}
