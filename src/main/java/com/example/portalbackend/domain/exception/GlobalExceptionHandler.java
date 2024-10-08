package com.example.portalbackend.domain.exception;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.example.portalbackend.util.error.ErrorHandleMessage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleError404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleError400(MethodArgumentNotValidException ex){
        List<FieldError> errors = ex.getFieldErrors();

        List<ValidationErrorData> messages = new ArrayList<>(errors.size());

        errors.forEach( error -> {
            if (messages.stream().anyMatch( errorData -> Objects.equals(errorData.field(), error.getField()))){
                ValidationErrorData data = messages.stream().filter(
                        errorData -> Objects.equals(errorData.field(), error.getField())
                ).findFirst().get();

                messages.remove(data);
                List<String> messagesList = data.messages();
                String errorMessage = error.getDefaultMessage();
                messagesList.add(errorMessage);
                messages.add(new ValidationErrorData(error.getField(), messagesList, LocalDate.now()));
            }else{
                messages.add(new ValidationErrorData(error));
            }
        });
        return ResponseEntity.badRequest().body(messages);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleError400(HttpMessageNotReadableException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleValidationError(ConstraintViolationException ex){
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        List<ValidationErrorData> messages = new ArrayList<>(constraintViolations.size());

        constraintViolations.forEach(
                constraintViolation -> {
                    if (messages.stream().anyMatch( errorData -> Objects.equals(errorData.field(), constraintViolation.getPropertyPath().toString()))){
                        ValidationErrorData data = messages.stream().filter( errorData -> Objects.equals(errorData.field(), constraintViolation.getPropertyPath().toString())
                        ).findFirst().get();

                        messages.remove(data);

                        List<String> messageList = data.messages();
                        messageList.add(constraintViolation.getMessage());
                        messages.add(new ValidationErrorData(constraintViolation.getPropertyPath().toString(), messageList, LocalDate.now()));
                    }

                    messages.add(
                            new ValidationErrorData(
                                    constraintViolation.getPropertyPath().toString(),
                                    Collections.singletonList(constraintViolation.getMessage()),
                                    LocalDate.now()));
                }
        );
        return ResponseEntity.badRequest().body(messages);
    }




    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<?> handleError500(JpaSystemException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getCause());
    }

   @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> handleError500(SQLException ex){
        String errorCode = ex.getSQLState();
        if (errorCode.equals("23505")){
            int index = ex.getMessage().indexOf("Detail:");
            String message = ex.getMessage().substring(index+8).replaceAll("[()]", "").replaceAll("=", ": ").replaceAll("Ya existe la llave", "Ya existe un registro con el mismo ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ErrorHandleMessage.constraintKeyToMessage(message));
        }
        else if(errorCode.equals("22001")){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No se pudo realizar la operación");
    }


    private record ValidationErrorData(String field, List<String> messages, LocalDate timestamp){
        public ValidationErrorData(FieldError error){
            this(error.getField(), new ArrayList<>(Collections.singletonList(error.getDefaultMessage())), LocalDate.now());
        }
    }

    @ExceptionHandler(FileEmptyException.class)
    public ResponseEntity<?> handleFileEmptyException(FileEmptyException ex){
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<?> handleFileUploadException(FileUploadException ex){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(SpringBootFileUploadException.class)
    public ResponseEntity<?> handleSpringBootFileUploadException(SpringBootFileUploadException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<?> handleAmazonServiceException(AmazonServiceException ex){
        return ResponseEntity.internalServerError().body(ex.getErrorMessage());
    }

    @ExceptionHandler(SdkClientException.class)
    public ResponseEntity<?> handleSdkClientException(SdkClientException ex){
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

    @ExceptionHandler({IOException.class, FileNotFoundException.class, MultipartException.class})
    public ResponseEntity<?> handleIOException(Exception ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }



}

