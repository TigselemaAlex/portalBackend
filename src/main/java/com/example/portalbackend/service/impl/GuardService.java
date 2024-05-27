package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.guard.GuardCreateData;
import com.example.portalbackend.api.dto.request.guard.GuardUpdateData;
import com.example.portalbackend.domain.entity.Guard;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.example.portalbackend.domain.repository.GuardRepository;
import com.example.portalbackend.service.spec.IGuardService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class GuardService implements IGuardService {

    private final GuardRepository guardRepository;
    private final FileService fileService;
    private final String BUCKET_URL = "https://portal-de-la-vina-bucket.s3.us-east-2.amazonaws.com/";
    @Override
    public Guard create(GuardCreateData data) throws IOException, FileUploadException {
        Guard guard = Guard.builder()
                .dni(data.dni())
                .fullName(data.fullName())
                .phone(data.phone())
                .build();
        String photoName = "";
        updateGuardPhoto(data.photo(), photoName, guard);
        return guardRepository.save(guard);
    }

    @Override
    public Guard update(Long id, GuardUpdateData data) throws IOException, FileUploadException {
        Guard guard = findById(id);
        guard.setDni(data.dni());
        guard.setFullName(data.fullName());
        guard.setPhone(data.phone());
        if (data.isPhotoUpdated()) {
            String photoName = "";
            fileService.delete(guard.getPhotoName());
            updateGuardPhoto(data.photo(), photoName, guard);
        }
        return guardRepository.save(guard);
    }

    private void updateGuardPhoto(MultipartFile photo, String photoName, Guard guard) throws IOException, FileUploadException {
        if (photo != null && photo.getSize() > 0) {
            photoName = fileService.uploadFile(photo, null);
            guard.setPhotoName(photoName);
            guard.setPhotoUrl(BUCKET_URL + photoName);
        }else {
            guard.setPhotoName(photoName);
            guard.setPhotoUrl(photoName);
        }
    }

    @Override
    public Page<Guard> findAllActiveGuards(String search, Pageable pageable) {
        return guardRepository.findAllByActiveIsTrue(search, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Guard> findAll(String search, Pageable pageable) {
        return guardRepository.findAllGuardsByFullNameContainingIgnoreCaseOrDniContainingIgnoreCase(search, search, pageable);
    }

    @Override
    public Guard findById(Long id) {
        return guardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void delete(Long id){
        Guard guard = findById(id);
        guardRepository.delete(guard);
    }

    @Override
    public void reactivate(Long id) {
        Guard guard = findById(id);
        guard.setActive(true);
        guardRepository.save(guard);
    }
}
