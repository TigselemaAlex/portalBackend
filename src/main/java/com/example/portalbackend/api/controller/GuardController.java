package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.guard.GuardCreateData;
import com.example.portalbackend.api.dto.request.guard.GuardUpdateData;
import com.example.portalbackend.api.usecase.GuardUseCase;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.domain.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/protected/guards")
@RequiredArgsConstructor
public class GuardController {

    private final GuardUseCase guardUseCase;

    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAllGuards(
            @RequestParam(defaultValue = "") String search,
            @PageableDefault(
                    size = 10,
                    sort = "updatedAt",
                    direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return guardUseCase.findAllGuards(search, pageable);
    }

    @GetMapping("/active")
    public ResponseEntity<CustomResponse<?>> findAllActiveGuards(
            @RequestParam(defaultValue = "") String search,
            @PageableDefault(
                    size = 10,
                    sort = "updatedAt",
                    direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return guardUseCase.findAllActive(search, pageable);
    }

    @PostMapping
    public ResponseEntity<CustomResponse<?>> createGuard(
            @RequestParam String dni,
            @RequestParam String fullName,
            @RequestParam String phone,
            @RequestParam(required = false) MultipartFile photo
    ) throws IOException, FileUploadException {
        return guardUseCase.createGuard(new GuardCreateData(fullName,dni,photo,phone));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> updateGuard(
            @PathVariable Long id,
            @RequestParam String dni,
            @RequestParam String fullName,
            @RequestParam String phone,
            @RequestParam(required = false) MultipartFile photo,
            @RequestParam Boolean isPhotoUpdated
    ) throws IOException, FileUploadException {
        return guardUseCase.updateGuard(id, new GuardUpdateData(fullName,dni,photo,isPhotoUpdated,phone));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> deleteGuard(@PathVariable Long id) {
        return guardUseCase.deleteGuard(id);
    }

    @PutMapping("/{id}/reactivate")
    public ResponseEntity<CustomResponse<?>> reactivateGuard(@PathVariable Long id) {
        return guardUseCase.reactivateGuard(id);
    }


}
