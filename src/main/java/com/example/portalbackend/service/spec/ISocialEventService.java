package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.social_event.SocialEventCreateData;
import com.example.portalbackend.domain.entity.SocialEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Calendar;
import java.util.Date;

public interface ISocialEventService {
    Page<SocialEvent> findAll(Calendar from, Calendar to, Pageable pageable);
    SocialEvent findById(Long id);
    SocialEvent create(SocialEventCreateData socialEvent);
}
