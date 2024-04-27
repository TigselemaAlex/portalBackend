package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.convocation.ConvocationCreateData;
import com.example.portalbackend.api.dto.response.PageResponse;
import com.example.portalbackend.api.dto.response.convocation.ConvocationResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.service.spec.IConvocationService;
import com.example.portalbackend.util.enumerate.ConvocationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ConvocationUseCase extends AbstractUseCase{

    private final IConvocationService convocationService;
    protected ConvocationUseCase(CustomResponseBuilder customResponseBuilder, IConvocationService convocationService) {
        super(customResponseBuilder);
        this.convocationService = convocationService;
    }

    public ResponseEntity<CustomResponse<?>> createConvocation(ConvocationCreateData data) {
        ConvocationResponse response = new ConvocationResponse(convocationService.createConvocation(data));
        return customResponseBuilder.build(HttpStatus.CREATED, "Convocatoria creada exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> findAll(String subject, Long start, Long end, ConvocationType type, Pageable pageable) {
        Page<ConvocationResponse> response = convocationService.findAll(subject, start, end, type, pageable).map(ConvocationResponse::new);
        PageResponse pageResponse = new PageResponse(response);
        return customResponseBuilder.build(HttpStatus.OK, "Convocatorias encontradas exitosamente", pageResponse);
    }
}
