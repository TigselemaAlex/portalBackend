package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.minutes.MinutesCreateData;
import com.example.portalbackend.domain.entity.Convocation;
import com.example.portalbackend.domain.entity.Minutes;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.example.portalbackend.domain.repository.MinutesRepository;
import com.example.portalbackend.service.spec.IConvocationService;
import com.example.portalbackend.service.spec.IMinuteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class MinutesService implements IMinuteService {

    private final MinutesRepository  minutesRepository;
    private  final IConvocationService convocationService;
    private final FileService fileService;
    private final String BUCKET_URL = "https://portal-de-la-vina-bucket.s3.us-east-2.amazonaws.com/";

    @Override
    public List<Minutes> findAllByConvocation(Long convocationId) {
        return minutesRepository.findByConvocation(convocationService.findById(convocationId));
    }

    @Override
    public Minutes save(MinutesCreateData minutes) throws IOException, FileUploadException {
        Convocation convocation = convocationService.findById(minutes.convocationId());
        String fileName = fileService.uploadFile(minutes.file(), convocation.getCode()+"_minutes");
        Minutes minutesToCreate = Minutes.builder()
                        .fileUrl(BUCKET_URL+fileName)
                        .fileName(fileName)
                        .convocation(convocation)
                        .build();
        convocation.getMinutes().add(minutesToCreate);
        return minutesRepository.save(minutesToCreate);
    }

    @Override
    public void delete(Long id) {
        minutesRepository.deleteById(id);
    }

}
