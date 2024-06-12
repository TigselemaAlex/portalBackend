package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.minutes.MinutesCreateData;
import com.example.portalbackend.domain.entity.Minutes;
import com.example.portalbackend.domain.exception.FileUploadException;

import java.io.IOException;
import java.util.List;

public interface IMinuteService {

    List<Minutes> findAllByConvocation(Long convocationId);

    Minutes save(MinutesCreateData minutes) throws IOException, FileUploadException;

    void delete(Long id);
}
