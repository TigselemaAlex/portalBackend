package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.social_event.SocialEventCreateData;
import com.example.portalbackend.api.dto.response.PageResponse;
import com.example.portalbackend.api.dto.response.social_event.SocialEventResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.service.spec.ISocialEventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Component
public class SocialEventUseCase extends AbstractUseCase{

    private final ISocialEventService socialEventService;
    protected SocialEventUseCase(CustomResponseBuilder customResponseBuilder, ISocialEventService socialEventService) {
        super(customResponseBuilder);
        this.socialEventService = socialEventService;
    }
    public ResponseEntity<CustomResponse<?>> createSocialEvent(final SocialEventCreateData data){
        SocialEventResponse response = new SocialEventResponse(socialEventService.create(data));
        return customResponseBuilder.build(HttpStatus.CREATED, "Evento social creado exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> findAll(Calendar from, Calendar to, Pageable pageable){
        Page<SocialEventResponse> socialEventResponses = socialEventService.findAll(from, to, pageable).map(SocialEventResponse::new);
        PageResponse response = new PageResponse(socialEventResponses);
        return customResponseBuilder.build(HttpStatus.OK, "Evento social obtenido exitosamente", response);
    }
}
