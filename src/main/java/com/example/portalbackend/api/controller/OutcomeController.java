package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.outcome.OutcomeCreateData;
import com.example.portalbackend.api.dto.request.outcome.OutcomeUpdateData;
import com.example.portalbackend.api.usecase.OutcomeUseCase;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.domain.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/protected/outcomes")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_PRESIDENT', 'ROLE_VICEPRESIDENT', 'ROLE_TREASURER', 'ROLE_ADMIN', 'ROLE_SECRETARY')")
public class OutcomeController {

    private final OutcomeUseCase outcomeUseCase;

    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAll(
            @RequestParam(required = false) Long type,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) Long provider,
            @RequestParam(required = false) Long from,
            @RequestParam(required = false) Long to,
            @PageableDefault(
                    size = 20,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC)
            Pageable pageable
    ){
        return outcomeUseCase.findAll(type, code, provider, from, to, pageable);
    }

    @PostMapping
    public ResponseEntity<CustomResponse<?>> save(
            @RequestParam String description,
            @RequestParam BigDecimal amount,
            @RequestParam Long paidDate,
            @RequestParam Long type,
            @RequestParam (required = false) Long provider,
            @RequestParam MultipartFile paidEvidence
    ) throws IOException, FileUploadException {
        return outcomeUseCase.save(
                new OutcomeCreateData(
                        description,
                        amount,
                        paidDate,
                        provider,
                        type,
                        paidEvidence
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> update(
            @PathVariable Long id,
            @RequestParam String description,
            @RequestParam BigDecimal amount,
            @RequestParam Long paidDate,
            @RequestParam Long type,
            @RequestParam(required = false) Long provider,
            @RequestParam(required = false) MultipartFile paidEvidence
    ) throws IOException, FileUploadException {
        return outcomeUseCase.update(
                id,
                new OutcomeUpdateData(
                        description,
                        amount,
                        paidDate,
                        provider,
                        type,
                        paidEvidence
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> delete(@PathVariable Long id){
        return outcomeUseCase.delete(id);
    }
}
