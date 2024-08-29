package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.usecase.SocialEventUseCase;
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

@RestController
@RequestMapping("/protected/social-event")
@RequiredArgsConstructor
public class SocialEventController {
    private final SocialEventUseCase socialEventUseCase;
    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAll(@RequestParam(defaultValue = "") String from,
                                                     @RequestParam(defaultValue = "") String to,
                                                     @RequestParam(defaultValue = "") String title,
                                                     @PageableDefault(
                                                             size = 10,
                                                             sort = "id",
                                                             direction = Sort.Direction.DESC)
                                                     Pageable pageable
                                                     ){
        return socialEventUseCase.findAll(from, to, title,pageable);
    }

    @PreAuthorize("hasAnyRole('ROLE_PRESIDENT', 'ROLE_VICEPRESIDENT', 'ROLE_TREASURER', 'ROLE_ADMIN', 'ROLE_SECRETARY')")
    @PostMapping
    public ResponseEntity<CustomResponse<?>> createSocialEvent(@RequestParam String title,
                                                              @RequestParam(required = false) String description,
                                                              @RequestParam String place,
                                                              @RequestParam String date,
                                                              @RequestParam(required = false) MultipartFile image,
                                                              @RequestParam Long createdBy) throws IOException, FileUploadException {
        return socialEventUseCase.createSocialEvent(title, description, place, date, image ,createdBy);
    }

    @PreAuthorize("hasAnyRole('ROLE_PRESIDENT', 'ROLE_VICEPRESIDENT', 'ROLE_TREASURER', 'ROLE_ADMIN', 'ROLE_SECRETARY')")
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> updateSocialEvent(@PathVariable Long id,
                                                              @RequestParam String title,
                                                              @RequestParam(required = false) String description,
                                                              @RequestParam String place,
                                                              @RequestParam String date,
                                                              @RequestParam(required = false) MultipartFile image,
                                                              @RequestParam Boolean isImageUpdated,
                                                              @RequestParam Long updatedBy) throws IOException, FileUploadException {
        return socialEventUseCase.updateSocialEvent(id, title, description, place, date, image, isImageUpdated,updatedBy);
    }

    @PreAuthorize("hasAnyRole('ROLE_PRESIDENT', 'ROLE_VICEPRESIDENT', 'ROLE_TREASURER', 'ROLE_ADMIN', 'ROLE_SECRETARY')")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> deleteSocialEvent(@PathVariable Long id){
        return socialEventUseCase.deleteSocialEvent(id);
    }
}
