package com.example.portalbackend.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.portalbackend.domain.exception.FileDownloadException;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.example.portalbackend.service.spec.IFileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileService implements IFileService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    private final AmazonS3 amazonS3;

    @Override
    public String uploadFile(MultipartFile multipartFile) throws FileUploadException, IOException {
        //Convert MultipartFile to File
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try(FileOutputStream fos = new FileOutputStream(file)){
            fos.write(multipartFile.getBytes());
        }
        //Generate the file name
        String fileName = generateFileName(multipartFile);
        //Upload the file to S3 bucket
        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, file);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
        metadata.addUserMetadata("Title", "File Upload-" + fileName);
        metadata.setContentLength(file.length());
        request.setMetadata(metadata);
        amazonS3.putObject(request);
        file.delete();
        return fileName;
    }

    @Override
    public Object downloadFile(String fileName) throws FileDownloadException, IOException {
        return null;
    }

    @Override
    public void delete(String fileName) {
        if (fileName == null || fileName.isEmpty()) return;
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, fileName);
        amazonS3.deleteObject(deleteObjectRequest);
    }

    private String generateFileName(MultipartFile multipartFile) {
        return Calendar.getInstance().getTimeInMillis() + "_" + Objects.requireNonNull(multipartFile.getOriginalFilename()).replaceAll(" ", "_");
    }
}
