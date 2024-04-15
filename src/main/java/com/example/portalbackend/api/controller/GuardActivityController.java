package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.guard_activity.GuardActivityCreateData;
import com.example.portalbackend.api.dto.request.guard_activity.GuardActivityUpdateData;
import com.example.portalbackend.api.usecase.GuardActivityUseCase;
import com.example.portalbackend.common.CustomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("protected/guard-activity")
@RequiredArgsConstructor
public class GuardActivityController {

    private final GuardActivityUseCase guardActivityUseCase;

    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAll(
            @RequestParam(defaultValue = "") String subject,
            @PageableDefault(
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC)
            Pageable pageable
    ){
        return guardActivityUseCase.findAll(subject, pageable);
    }

    @PostMapping
    public ResponseEntity<CustomResponse<?>> create(@Valid @RequestBody GuardActivityCreateData data) {
        return guardActivityUseCase.createActivity(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> update(@PathVariable Long id, @Valid @RequestBody GuardActivityUpdateData data) {
        return guardActivityUseCase.updateActivity(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> delete(@PathVariable Long id) {
        return guardActivityUseCase.deleteActivity(id);
    }

}
