package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.penalty.PenaltyCreateData;
import com.example.portalbackend.api.dto.request.penalty.PenaltyUpdateData;
import com.example.portalbackend.domain.entity.Penalty;
import com.example.portalbackend.domain.entity.PenaltyType;
import com.example.portalbackend.domain.entity.Residence;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.example.portalbackend.util.enumerate.PaidStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Calendar;

public interface IPenaltyService {

    Page<Penalty> findAllActive(PenaltyType type, Calendar from, Calendar to, Residence residence, PaidStatus status, Pageable pageable);

    Penalty findById(Long id);

    Penalty save(PenaltyCreateData data) throws IOException, FileUploadException;

    Penalty update(Long id, PenaltyUpdateData data) throws IOException, FileUploadException;

    void deleteById(Long id);

}
