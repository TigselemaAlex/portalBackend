package com.example.portalbackend.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@JsonPropertyOrder({"status", "message", "data"})
public class CustomResponse <T> {
    private final HttpStatus status;
    private final String message;
    private final T data;

    private CustomResponse(ResponseBuilder<T> builder){
        this.status = builder.status;
        this.message = builder.message;
        this.data = builder.data;
    }

    public static class ResponseBuilder<T>{
        private final HttpStatus status;
        private String message;
        private T data;

        public ResponseBuilder(HttpStatus status, String message){
            this.status = status;
            this.message = message;
        }

        public ResponseBuilder<T> data (T data){
            this.data = data;
            return this;
        }

        public ResponseEntity<CustomResponse<?>> build(){
            CustomResponse<T> customResponse = new CustomResponse<>(this);
            return new ResponseEntity<>(customResponse, this.status);
        }
    }
}
