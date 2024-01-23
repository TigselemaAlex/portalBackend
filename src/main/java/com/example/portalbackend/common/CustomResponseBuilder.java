package com.example.portalbackend.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomResponseBuilder {

    public ResponseEntity<CustomResponse<?>> build(HttpStatus status, String message){
        return new CustomResponse.ResponseBuilder<>(status, message).build();
    }
    public <T> ResponseEntity<CustomResponse<?>> build(HttpStatus status, String message, T data){
        return new CustomResponse.ResponseBuilder<>(status, message).data(data).build();
    }
}
