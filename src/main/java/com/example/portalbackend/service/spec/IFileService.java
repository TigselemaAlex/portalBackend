package com.example.portalbackend.service.spec;

import com.example.portalbackend.domain.exception.FileDownloadException;
import com.example.portalbackend.domain.exception.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {

    String uploadFile(MultipartFile multipartFile) throws FileUploadException, IOException;

    Object downloadFile(String fileName) throws FileDownloadException, IOException;

    void delete(String fileName);
}
