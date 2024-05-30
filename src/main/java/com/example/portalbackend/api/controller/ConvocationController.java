package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.convocation.ConvocationAttendanceData;
import com.example.portalbackend.api.dto.request.convocation.ConvocationCreateData;
import com.example.portalbackend.api.dto.request.convocation.ConvocationParticipantAttendanceData;
import com.example.portalbackend.api.dto.request.convocation.ConvocationUpdateData;
import com.example.portalbackend.api.usecase.ConvocationUseCase;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.util.enumerate.ConvocationType;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/protected/convocation")
@RequiredArgsConstructor
public class ConvocationController {
    private final ConvocationUseCase convocationUseCase;

    @PostMapping
    public ResponseEntity<CustomResponse<?>> createConvocation(@Valid @RequestBody ConvocationCreateData data) throws FirebaseMessagingException {
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


    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> updateConvocation(
            @PathVariable Long id,
            @Valid @RequestBody ConvocationUpdateData data
    ) {
        return convocationUseCase.updateConvocation(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> deleteConvocation(@PathVariable Long id) {
        return convocationUseCase.deleteConvocation(id);
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<CustomResponse<?>> findAllParticipants(@PathVariable Long id) {
        return convocationUseCase.findAllParticipants(id);
    }

    @PutMapping("/{id}/finalize")
    public ResponseEntity<CustomResponse<?>> finalizeConvocation(@PathVariable Long id) {
        convocationUseCase.finalizeConvocation(id);
        return convocationUseCase.finalizeConvocation(id);
    }

    @PutMapping("/{id}/attendance")
    public ResponseEntity<CustomResponse<?>> updateAttendance(
            @PathVariable Long id,
            @Valid @RequestBody ConvocationAttendanceData data
    ) {
        return convocationUseCase.updateAttendance(id, data);
    }


    @GetMapping("/today-not-finalized")
    public ResponseEntity<CustomResponse<?>> findTodayNotFinalizedConvocation() {
        return convocationUseCase.findFirstByDateBetweenAndFinalizedIsFalse();
    }

    @GetMapping("/{id}/convocation-participants/{userId}")
    public ResponseEntity<CustomResponse<?>> findAllByConvocationIdAndResidenceUserId(@PathVariable Long id, @PathVariable Long userId) {
        return convocationUseCase.findAllByConvocationIdAndResidenceUserId(id, userId);
    }

    @PutMapping ("/{id}/participant-attendance")
    public ResponseEntity<CustomResponse<?>> updateParticipantAttendance(@PathVariable Long id, @Valid @RequestBody ConvocationParticipantAttendanceData data) throws IOException {
        return convocationUseCase.setParticipantAttendance(id, data);
    }

    @GetMapping("/calendar")
    public ResponseEntity<CustomResponse<?>> findAllByActiveIsTrueAndTypeInAndDateGreaterThanEqual(@PageableDefault(size = 10,
            sort = "createdAt",
            direction = Sort.Direction.ASC) Pageable pageable) {
        return convocationUseCase.findAllByActiveIsTrueAndTypeInAndDateGreaterThanEqual(pageable);
    }

}
