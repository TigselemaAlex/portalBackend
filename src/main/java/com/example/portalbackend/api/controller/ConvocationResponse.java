package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.convocation.ConvocationCreateData;
import com.example.portalbackend.api.usecase.ConvocationUseCase;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.util.enumerate.ConvocationType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/protected/convocation")
@RequiredArgsConstructor
public class ConvocationResponse {
    private final ConvocationUseCase convocationUseCase;

    @PostMapping
    public ResponseEntity<CustomResponse<?>> createConvocation(@Valid @RequestBody ConvocationCreateData data) {
        return convocationUseCase.createConvocation(data);
    }

    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAllConvocations(
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) Long start,
            @RequestParam(required = false) Long end,
            @RequestParam(required = false) ConvocationType type,
            @PageableDefault(size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return convocationUseCase.findAll(subject, start, end, type, pageable);
    }
}
