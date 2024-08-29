package com.example.portalbackend.api.usecase;


import com.example.portalbackend.api.dto.request.minutes.MinutesCreateData;
import com.example.portalbackend.api.dto.response.minutes.MinutesResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.example.portalbackend.service.spec.IMinuteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class MinuteUseCase extends AbstractUseCase{

    private final IMinuteService minuteService;
    protected MinuteUseCase(CustomResponseBuilder customResponseBuilder, IMinuteService minuteService) {
        super(customResponseBuilder);
        this.minuteService = minuteService;
    }


    public ResponseEntity<CustomResponse<?>> findAllByConvocation(Long convocationId){
        List<MinutesResponse> responses = minuteService.findAllByConvocation(convocationId).stream().map(MinutesResponse::new).toList();
        return customResponseBuilder.build(HttpStatus.OK, "Listado de actas", responses);
    }

    public ResponseEntity<CustomResponse<?>> save(MinutesCreateData minutes) {
        try {
            return customResponseBuilder.build(HttpStatus.CREATED, "Acta creada", new MinutesResponse(minuteService.save(minutes)));
        } catch (IOException | FileUploadException e) {
            return customResponseBuilder.build(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public ResponseEntity<CustomResponse<?>> delete(Long id) {
        minuteService.delete(id);
        return customResponseBuilder.build(HttpStatus.OK, "Acta eliminada");
    }
}
