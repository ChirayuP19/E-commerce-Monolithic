package com.ecommerce.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyGlobeExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> myMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String,String> response =new HashMap<>();
        methodArgumentNotValidException.getBindingResult()
                .getAllErrors().forEach(err->{
                    String fieldName =((FieldError)err).getField();
                    String message = err.getDefaultMessage();
                    response.put(fieldName,message);
                });

        Map<String,Object> response1=new HashMap<>();
        response1.put("TimeStamp", LocalDateTime.now());
        response1.put("Status",HttpStatus.BAD_REQUEST);
        response1.put("Message",methodArgumentNotValidException.getFieldError().getDefaultMessage());

        return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);
    }
}
