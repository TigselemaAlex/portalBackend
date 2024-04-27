package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.guard_activity.GuardActivityCreateData;
import com.example.portalbackend.api.dto.request.guard_activity.GuardActivityUpdateData;
import com.example.portalbackend.api.dto.response.PageResponse;
import com.example.portalbackend.api.dto.response.guard_activity.GuardActivityResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.domain.entity.GuardActivity;
import com.example.portalbackend.service.spec.IGuardActivityService;
import com.example.portalbackend.util.enumerate.GuardActivityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class GuardActivityUseCase extends AbstractUseCase{

    private final IGuardActivityService guardActivityService;
    protected GuardActivityUseCase(CustomResponseBuilder customResponseBuilder, IGuardActivityService guardActivityService) {
        super(customResponseBuilder);
        this.guardActivityService = guardActivityService;
    }

    public ResponseEntity<CustomResponse<?>> createActivity(GuardActivityCreateData data) {
        GuardActivity guardActivity = guardActivityService.create(data);
        GuardActivityResponse response = new GuardActivityResponse(guardActivity);
        return customResponseBuilder.build(HttpStatus.CREATED, "Actividad de guardianía creada exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> findAll(String subject, Long start, Long end, GuardActivityStatus status, Long guard, Long createdBy, Pageable pageable){
        Page<GuardActivityResponse> guardActivityResponses = guardActivityService.findAll(subject, start, end, status, guard, createdBy, pageable).map(GuardActivityResponse::new);
        PageResponse response = new PageResponse(guardActivityResponses);
        return customResponseBuilder.build(HttpStatus.OK, "Actividades de guardianía encontradas exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> updateActivity(Long id, GuardActivityUpdateData data){
        GuardActivity guardActivity = guardActivityService.update(id, data);
        GuardActivityResponse response = new GuardActivityResponse(guardActivity);
        return customResponseBuilder.build(HttpStatus.OK, "Actividad de guardianía actualizada exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> deleteActivity(Long id){
        guardActivityService.delete(id);
        return customResponseBuilder.build(HttpStatus.OK, "Actividad de guardianía eliminada exitosamente");
    }
}
