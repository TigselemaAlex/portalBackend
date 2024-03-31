package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.social_event.SocialEventCreateData;
import com.example.portalbackend.api.dto.request.social_event.SocialEventUpdateData;
import com.example.portalbackend.domain.entity.SocialEvent;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.example.portalbackend.domain.repository.SocialEventRepository;
import com.example.portalbackend.service.spec.IFileService;
import com.example.portalbackend.service.spec.ISocialEventService;
import com.example.portalbackend.service.spec.IUserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@Service
@Transactional
public class SocialEventService implements ISocialEventService {


    private final SocialEventRepository socialEventRepository;
    private final IUserService userService;
    private final IFileService fileService;

    private final String BUCKET_URL = "https://portal-de-la-vina-bucket.s3.us-east-2.amazonaws.com/";

    public SocialEventService(SocialEventRepository socialEventRepository, IUserService userService, IFileService fileService) {
        this.socialEventRepository = socialEventRepository;
        this.userService = userService;
        this.fileService = fileService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SocialEvent> findAll(Calendar from, Calendar to, String title, Pageable pageable) {
        if (from == null && to == null) {
            return socialEventRepository.findAllByTitleContainingIgnoreCase(title,pageable);
        } else if (from == null) {
            return socialEventRepository.findAllByTitleAndDateBefore(title, to,pageable);
        } else if (to == null) {
            return socialEventRepository.findAllByTitleAndDateAfter(title, from,pageable);
        } else{
            return socialEventRepository.findAllByDateBetweenAndTitleContainingIgnoreCase(from, to,title ,pageable);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public SocialEvent findById(Long id) {
        return socialEventRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public SocialEvent create(@Valid SocialEventCreateData socialEvent) throws IOException, FileUploadException {
        SocialEvent newSocialEvent = SocialEvent.builder()
                .title(socialEvent.title())
                .description(socialEvent.description())
                .place(socialEvent.place())
                .date(socialEvent.date())
                .createdBy(userService.findById(socialEvent.createdBy()))
                .updatedBy(userService.findById(socialEvent.createdBy()))
                .build();
        String fileName = "";
        if (socialEvent.image() != null && socialEvent.image().getSize() > 0) {
            fileName = BUCKET_URL +  fileService.uploadFile(socialEvent.image());
        }
        newSocialEvent.setImageUrl(  fileName);
        newSocialEvent.setFileName(fileName);
        return socialEventRepository.save(newSocialEvent);
    }

    @Override
    public SocialEvent update(Long id, SocialEventUpdateData socialEvent) throws IOException, FileUploadException {
        SocialEvent socialEventToUpdate = findById(id);
        socialEventToUpdate.setTitle(socialEvent.title());
        socialEventToUpdate.setDescription(socialEvent.description());
        socialEventToUpdate.setPlace(socialEvent.place());
        socialEventToUpdate.setDate(socialEvent.date());
        socialEventToUpdate.setUpdatedBy(userService.findById(socialEvent.updatedBy()));
        if (socialEvent.isImageUpdated()) {
            String fileName = "";
            if (socialEvent.image() != null && socialEvent.image().getSize() > 0) {
                fileName = BUCKET_URL + fileService.uploadFile(socialEvent.image());
            }else{
                fileService.delete(socialEventToUpdate.getFileName());
            }
            socialEventToUpdate.setImageUrl(fileName);
            socialEventToUpdate.setFileName(fileName);
        }
        return socialEventRepository.save(socialEventToUpdate);
    }

    @Override
    public void delete(Long id) {
        SocialEvent socialEvent = findById(id);
        fileService.delete(socialEvent.getFileName());
        socialEventRepository.delete(socialEvent);
    }
}
