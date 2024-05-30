package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.outcome.OutcomeCreateData;
import com.example.portalbackend.api.dto.request.outcome.OutcomeUpdateData;
import com.example.portalbackend.api.dto.response.PageResponse;
import com.example.portalbackend.api.dto.response.outcome.OutcomeResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.domain.entity.OutcomeType;
import com.example.portalbackend.domain.entity.Provider;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.example.portalbackend.service.spec.IOutcomeService;
import com.example.portalbackend.service.spec.IOutcomeTypeService;
import com.example.portalbackend.service.spec.IProviderService;
import com.example.portalbackend.util.calendar.CalendarUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;

@Component
public class OutcomeUseCase extends AbstractUseCase{

    private final IOutcomeService outcomeService;
    private final IProviderService providerService;
    private final IOutcomeTypeService outcomeTypeService;
    protected OutcomeUseCase(CustomResponseBuilder customResponseBuilder, IOutcomeService outcomeService, IProviderService providerService, IOutcomeTypeService outcomeTypeService) {
        super(customResponseBuilder);
        this.outcomeService = outcomeService;
        this.providerService = providerService;
        this.outcomeTypeService = outcomeTypeService;
    }

    public ResponseEntity<CustomResponse<?>> findAll(Long type, String code, Long provider, Long from , Long to, Pageable pageable){
        OutcomeType outcomeTypeEntity = null == type ? null : outcomeTypeService.findById(type);
        Provider providerEntity = null == provider ? null : providerService.findById(provider);
        Page<OutcomeResponse> responses = outcomeService.findAll(outcomeTypeEntity, code, providerEntity, CalendarUtil.getCalendar(from), CalendarUtil.getCalendar(to), pageable)
                .map(OutcomeResponse::new);
        PageResponse pageResponse = new PageResponse(responses);
        return customResponseBuilder.build(HttpStatus.OK, "Listado de egresos", pageResponse);
    }

    public ResponseEntity<CustomResponse<?>> save(OutcomeCreateData data) throws IOException, FileUploadException {
        outcomeService.save(data);
        return customResponseBuilder.build(HttpStatus.CREATED, "Egreso creado");
    }

    public ResponseEntity<CustomResponse<?>> update(Long id, OutcomeUpdateData data) throws IOException, FileUploadException {
        outcomeService.update(id, data);
        return customResponseBuilder.build(HttpStatus.OK, "Egreso actualizado");
    }

    public ResponseEntity<CustomResponse<?>> delete(Long id){
        outcomeService.delete(id);
        return customResponseBuilder.build(HttpStatus.OK, "Egreso eliminado");
    }
}
