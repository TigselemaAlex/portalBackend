package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.minutes.MinutesCreateData;
import com.example.portalbackend.api.usecase.MinuteUseCase;
import com.example.portalbackend.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/protected/minutes")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_PRESIDENT', 'ROLE_VICEPRESIDENT', 'ROLE_TREASURER', 'ROLE_ADMIN', 'ROLE_SECRETARY')")
public class MinutesController {

    private final MinuteUseCase minuteUseCase;

    @GetMapping("/convocation/{id}")
    public ResponseEntity<CustomResponse<?>> findAllByConvocation(@PathVariable Long id){
        return minuteUseCase.findAllByConvocation(id);
    }

    @PostMapping
    public ResponseEntity<CustomResponse<?>> save(@RequestParam Long convocationId, @RequestParam MultipartFile file){
        return minuteUseCase.save(new MinutesCreateData(convocationId, file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> delete(@PathVariable Long id){
        return minuteUseCase.delete(id);
    }
}
