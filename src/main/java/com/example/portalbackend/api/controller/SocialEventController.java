package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.social_event.SocialEventSearchData;
import com.example.portalbackend.api.usecase.SocialEventUseCase;
import com.example.portalbackend.common.CustomResponse;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected/social-event")
public class SocialEventController {
    private final SocialEventUseCase socialEventUseCase;
    public SocialEventController(SocialEventUseCase socialEventUseCase) {
        this.socialEventUseCase = socialEventUseCase;
    }
    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAll(@RequestBody SocialEventSearchData search,
                                                     @PageableDefault(
                                                             size = 10,
                                                             sort = "id",
                                                             direction = Sort.Direction.DESC)
                                                     Pageable pageable
                                                     ){
        return socialEventUseCase.findAll(search.from(), search.to(), pageable);
    }
}
