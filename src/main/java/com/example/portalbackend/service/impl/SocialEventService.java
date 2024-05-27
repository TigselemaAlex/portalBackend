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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class SocialEventService implements ISocialEventService {


    private final SocialEventRepository socialEventRepository;
    private final IUserService userService;
    private final IFileService fileService;

    private final String BUCKET_URL = "https://portal-de-la-vina-bucket.s3.us-east-2.amazonaws.com/";

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
        updateFile(newSocialEvent, fileName, socialEvent.image());
        return socialEventRepository.save(newSocialEvent);
    }

    private void updateFile(SocialEvent newSocialEvent, String fileName, MultipartFile image) throws FileUploadException, IOException {
        if (image != null && image.getSize() > 0) {
            fileName = fileService.uploadFile(image, null);
            newSocialEvent.setImageUrl(BUCKET_URL + fileName);
            newSocialEvent.setFileName(fileName);
        }else{
            newSocialEvent.setImageUrl(fileName);
            newSocialEvent.setFileName(fileName);
        }
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
            fileService.delete(socialEventToUpdate.getFileName());
            updateFile(socialEventToUpdate, fileName, socialEvent.image());
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
