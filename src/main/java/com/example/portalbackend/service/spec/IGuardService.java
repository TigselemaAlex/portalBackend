package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.guard.GuardCreateData;
import com.example.portalbackend.api.dto.request.guard.GuardUpdateData;
import com.example.portalbackend.domain.entity.Guard;
import com.example.portalbackend.domain.exception.FileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface IGuardService {
    Guard create(GuardCreateData data) throws IOException, FileUploadException;

    Guard update(Long id, GuardUpdateData data) throws IOException, FileUploadException;

    Page<Guard> findAllActiveGuards(String search, Pageable pageable);

    Page<Guard> findAll(String search, Pageable pageable);

    Guard findById(Long id);

    void delete(Long id);

    void reactivate(Long id);
}
