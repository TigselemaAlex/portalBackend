package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.social_event.SocialEventCreateData;
import com.example.portalbackend.api.dto.request.social_event.SocialEventUpdateData;
import com.example.portalbackend.domain.entity.SocialEvent;
import com.example.portalbackend.domain.exception.FileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public interface ISocialEventService {
    Page<SocialEvent> findAll(Calendar from, Calendar to, String title, Pageable pageable);
    SocialEvent findById(Long id);
    SocialEvent create(SocialEventCreateData socialEvent) throws IOException, FileUploadException;
    SocialEvent update(Long id, SocialEventUpdateData socialEvent) throws IOException, FileUploadException;
    void delete(Long id);
}
