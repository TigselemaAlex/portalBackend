package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.penalty.PenaltyCreateData;
import com.example.portalbackend.api.dto.request.penalty.PenaltyUpdateData;
import com.example.portalbackend.api.usecase.PenaltyUseCase;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.example.portalbackend.util.enumerate.PaidStatus;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/protected/penalties")
@RequiredArgsConstructor
public class PenaltyController {

    private final PenaltyUseCase penaltyUseCase;

    @GetMapping("/active")
    public ResponseEntity<CustomResponse<?>> findAllActive(
            @RequestParam(required = false) Long type,
            @RequestParam(required = false) Long from,
            @RequestParam(required = false) Long to,
            @RequestParam(required = false) Long residence,
            @RequestParam(required = false) PaidStatus status,
            @PageableDefault(
                    size = 20,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC)
            Pageable pageable) {
        return penaltyUseCase.findAllActive(type, from, to, residence, status, pageable);
    }

    @PostMapping
    public ResponseEntity<CustomResponse<?>> save(
            @RequestParam String description,
            @RequestParam Long issueDate,
            @RequestParam(required = false) Long paidDate,
            @RequestParam BigDecimal amount,
            @RequestParam Long residence,
            @RequestParam PaidStatus status,
            @RequestParam Long type,
            @RequestParam(required = false)  List<MultipartFile> files,
            @RequestParam(required = false) MultipartFile paidEvidence
    ) throws IOException, FileUploadException, FirebaseMessagingException {
        return penaltyUseCase.save( new PenaltyCreateData(
                description,
                issueDate,
                paidDate,
                amount,
                residence,
                status,
                type,
                files,
                paidEvidence
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> update(
            @PathVariable Long id,
            @RequestParam String description,
            @RequestParam Long issueDate,
            @RequestParam(required = false) Long paidDate,
            @RequestParam BigDecimal amount,
            @RequestParam Long residence,
            @RequestParam PaidStatus status,
            @RequestParam Long type,
            @RequestParam(required = false) List<MultipartFile> files,
            @RequestParam(required = false) List<Long> filesToDelete,
            @RequestParam(required = false) MultipartFile paidEvidence
    ) throws IOException, FileUploadException, FirebaseMessagingException {
        return penaltyUseCase.update(id, new PenaltyUpdateData(
                description,
                issueDate,
                paidDate,
                amount,
                residence,
                status,
                type,
                files,
                filesToDelete,
                paidEvidence
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> delete(@PathVariable Long id) {
        return penaltyUseCase.delete(id);
    }


}
