package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.social_event.SocialEventCreateData;
import com.example.portalbackend.domain.entity.SocialEvent;
import com.example.portalbackend.domain.repository.SocialEventRepository;
import com.example.portalbackend.service.spec.ISocialEventService;
import com.example.portalbackend.service.spec.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@Service
@Transactional
public class SocialEventService implements ISocialEventService {

    private final SocialEventRepository socialEventRepository;
    private final IUserService userService;

    public SocialEventService(SocialEventRepository socialEventRepository, IUserService userService) {
        this.socialEventRepository = socialEventRepository;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SocialEvent> findAll(Calendar from, Calendar to, Pageable pageable) {
        return socialEventRepository.findAllByDateBetween(from, to, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public SocialEvent findById(Long id) {
        return socialEventRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public SocialEvent create(SocialEventCreateData socialEvent) {
        SocialEvent newSocialEvent = SocialEvent.builder()
                .title(socialEvent.title())
                .description(socialEvent.description())
                .place(socialEvent.place())
                .date(socialEvent.date())
                .imageUrl(socialEvent.imageUrl())
                .createdBy(userService.findById(socialEvent.createdBy()))
                .updatedBy(userService.findById(socialEvent.createdBy()))
                .build();
        return socialEventRepository.save(newSocialEvent);
    }
}
