package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.guard_activity.GuardActivityCreateData;
import com.example.portalbackend.api.dto.request.guard_activity.GuardActivityUpdateData;
import com.example.portalbackend.api.usecase.GuardActivityUseCase;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.util.enumerate.GuardActivityStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("protected/guard-activity")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_PRESIDENT', 'ROLE_VICEPRESIDENT', 'ROLE_TREASURER', 'ROLE_ADMIN', 'ROLE_SECRETARY')")
public class GuardActivityController {

    private final GuardActivityUseCase guardActivityUseCase;

    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAll(
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) Long start,
            @RequestParam(required = false) Long end,
            @RequestParam(required = false) Long guard,
            @RequestParam(required = false) Long createdBy,
            @RequestParam(required = false) GuardActivityStatus status,
            @PageableDefault(
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC)
            Pageable pageable
    ){
        return guardActivityUseCase.findAll(subject, start, end, status, guard, createdBy, pageable);
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
