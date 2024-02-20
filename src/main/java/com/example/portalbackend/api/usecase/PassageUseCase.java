package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.passage.PassageCreateData;
import com.example.portalbackend.api.dto.request.passage.PassageUpdateData;
import com.example.portalbackend.api.dto.response.PageResponse;
import com.example.portalbackend.api.dto.response.passage.PassageResponse;
import com.example.portalbackend.api.dto.response.user.UserResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.domain.entity.Passage;
import com.example.portalbackend.service.spec.IPassageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PassageUseCase extends AbstractUseCase {
    private final IPassageService passageService;

    protected PassageUseCase(CustomResponseBuilder customResponseBuilder, IPassageService passageService) {
        super(customResponseBuilder);
        this.passageService = passageService;
    }

    public ResponseEntity<CustomResponse<?>> createPassage(PassageCreateData passageCreateData) {
        PassageResponse passageResponse = new PassageResponse(passageService.create(passageCreateData));
        return customResponseBuilder.build(HttpStatus.CREATED, "Pasaje creado exitosamente", passageResponse);
    }

    public ResponseEntity<CustomResponse<?>> deletePassage(Long id) {
        passageService.delete(id);
        return customResponseBuilder.build(HttpStatus.OK, "Pasaje eliminado exitosamente");
    }

    public ResponseEntity<CustomResponse<?>> updatePassage(Long id, PassageUpdateData passageCreateData) {
        PassageResponse passageResponse = new PassageResponse(passageService.update(id, passageCreateData));
        return customResponseBuilder.build(HttpStatus.OK, "Pasaje actualizado exitosamente", passageResponse);
    }

    public ResponseEntity<CustomResponse<?>> findAllPassages(String name, Pageable pageable) {
        Page<PassageResponse> responses = passageService.findAll(name, pageable).map(PassageResponse::new);
        PageResponse response = new PageResponse(responses);
        return customResponseBuilder.build(HttpStatus.OK, "Listado de pasajes obtenido exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> findAllActivePassages() {
        List<Passage> passages = passageService.findAllActive();
        List<PassageResponse> responses = passages.stream().map(PassageResponse::new).toList();
        return customResponseBuilder.build(HttpStatus.OK, "Listado de pasajes activos obtenido exitosamente", responses);
    }

}
