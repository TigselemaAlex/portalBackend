package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.passage.PassageCreateData;
import com.example.portalbackend.api.dto.request.passage.PassageUpdateData;
import com.example.portalbackend.api.usecase.PassageUseCase;
import com.example.portalbackend.common.CustomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/protected/passages")
@RequiredArgsConstructor
public class PassageController {

    private final PassageUseCase passageUseCase;

    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAll(
            @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(defaultValue = "") String search) {
        return passageUseCase.findAllPassages(search, pageable);
    }

    @GetMapping(value = "/active")
    public ResponseEntity<CustomResponse<?>> findAllActive() {
        return passageUseCase.findAllActivePassages();
    }

    @PostMapping
    public ResponseEntity<CustomResponse<?>> create(@Valid @RequestBody PassageCreateData passageCreateData) {
        return passageUseCase.createPassage(passageCreateData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> delete(@PathVariable Long id) {
        return passageUseCase.deletePassage(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> update(@Valid @RequestBody PassageUpdateData passageUpdateData, @PathVariable Long id) {
        return passageUseCase.updatePassage(id, passageUpdateData);
    }
}
