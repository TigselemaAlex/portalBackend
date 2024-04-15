package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.social_event.SocialEventCreateData;
import com.example.portalbackend.api.dto.request.social_event.SocialEventUpdateData;
import com.example.portalbackend.api.dto.response.PageResponse;
import com.example.portalbackend.api.dto.response.social_event.SocialEventResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.example.portalbackend.service.spec.ISocialEventService;
import com.example.portalbackend.util.calendar.CalendarUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Component
public class SocialEventUseCase extends AbstractUseCase{

    private final ISocialEventService socialEventService;
    protected SocialEventUseCase(CustomResponseBuilder customResponseBuilder, ISocialEventService socialEventService) {
        super(customResponseBuilder);
        this.socialEventService = socialEventService;
    }
    public ResponseEntity<CustomResponse<?>> createSocialEvent(final String title,
                                                               final String description,
                                                               final String place,
                                                               final String date,
                                                               final MultipartFile image,
                                                               final Long createdBy) throws IOException, FileUploadException {
        SocialEventCreateData data = new SocialEventCreateData(title, description, place, CalendarUtil.getCalendar(date), image, createdBy);
        SocialEventResponse response = new SocialEventResponse(socialEventService.create(data));
        return customResponseBuilder.build(HttpStatus.CREATED, "Evento social creado exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> findAll(String from, String to, String title,Pageable pageable){
        Page<SocialEventResponse> socialEventResponses = socialEventService
                .findAll(CalendarUtil.getCalendar(from),
                        CalendarUtil.getCalendar(to), title,pageable)
                .map(SocialEventResponse::new);
        PageResponse response = new PageResponse(socialEventResponses);
        return customResponseBuilder.build(HttpStatus.OK, "Evento social obtenido exitosamente", response);
    }



    public ResponseEntity<CustomResponse<?>> updateSocialEvent(final Long id,
                                                               final String title,
                                                               final String description,
                                                               final String place,
                                                               final String date,
                                                               final MultipartFile image,
                                                               final Boolean isImageUpdated,
                                                               final Long updatedBy) throws IOException, FileUploadException {
        SocialEventUpdateData data = new SocialEventUpdateData(title, description, place, CalendarUtil.getCalendar(date), image, isImageUpdated, updatedBy);
        SocialEventResponse response = new SocialEventResponse(socialEventService.update(id, data));
        return customResponseBuilder.build(HttpStatus.OK, "Evento social actualizado exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> deleteSocialEvent(final Long id){
        socialEventService.delete(id);
        return customResponseBuilder.build(HttpStatus.OK, "Evento social eliminado exitosamente", null);
    }
}
