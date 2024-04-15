package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.guard.GuardCreateData;
import com.example.portalbackend.api.dto.request.guard.GuardUpdateData;
import com.example.portalbackend.api.dto.response.PageResponse;
import com.example.portalbackend.api.dto.response.guard.GuardResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.domain.entity.Guard;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.example.portalbackend.service.spec.IGuardActivityService;
import com.example.portalbackend.service.spec.IGuardService;
import com.example.portalbackend.util.enumerate.GuardActivityStatus;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GuardUseCase extends AbstractUseCase{
    private final IGuardService guardService;
    private final IGuardActivityService guardActivityService;
    protected GuardUseCase(CustomResponseBuilder customResponseBuilder, IGuardService guardService, IGuardActivityService guardActivityService) {
        super(customResponseBuilder);
        this.guardService = guardService;
        this.guardActivityService = guardActivityService;
    }
    public ResponseEntity<CustomResponse<?>> createGuard(@Valid GuardCreateData data) throws IOException, FileUploadException {
        Guard guard = guardService.create(data);
        GuardResponse response = new GuardResponse(guard);
        return customResponseBuilder.build(HttpStatus.CREATED, "Guardia creado exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> findAllGuards(String search, Pageable pageable) {
        Page<GuardResponse> guards = guardService.findAll(search, pageable).map(guard -> {
            Long pendingActivities = guardActivityService.countByGuardAndStatus(guard, GuardActivityStatus.PENDING);
            Long inProgressActivities = guardActivityService.countByGuardAndStatus(guard, GuardActivityStatus.IN_PROGRESS);
            Long finishedActivities = guardActivityService.countByGuardAndStatus(guard, GuardActivityStatus.FINISHED);
            return new GuardResponse(guard, pendingActivities, inProgressActivities, finishedActivities);
        });
        PageResponse response = new PageResponse(guards);
        return customResponseBuilder.build(HttpStatus.OK, "Listado de guardias obtenido exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> findAllActive(String search, Pageable pageable) {
        Page<GuardResponse> guards = guardService.findAllActiveGuards(search, pageable).map(GuardResponse::new);
        PageResponse response = new PageResponse(guards);
        return customResponseBuilder.build(HttpStatus.OK, "Listado de guardias activos obtenido exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> updateGuard(Long id, @Valid GuardUpdateData data) throws IOException, FileUploadException {
        Guard guard = guardService.update(id, data);
        GuardResponse response = new GuardResponse(guard);
        return customResponseBuilder.build(HttpStatus.OK, "Guardia actualizado exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> deleteGuard(Long id) {
        guardService.delete(id);
        return customResponseBuilder.build(HttpStatus.OK, "Guardia eliminado exitosamente");
    }

    public ResponseEntity<CustomResponse<?>> reactivateGuard(Long id) {
        guardService.reactivate(id);
        return customResponseBuilder.build(HttpStatus.OK, "Guardia reactivado exitosamente");
    }
}
