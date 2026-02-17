package com.ecommerce.project.exceptions;

import com.ecommerce.project.payload.APIResponse;
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

        Map<String,Object> responsePrint=new HashMap<>();
        responsePrint.put("TimeStamp", LocalDateTime.now());
        responsePrint.put("Status",HttpStatus.BAD_REQUEST);
        responsePrint.put("Message",methodArgumentNotValidException.getFieldError().getDefaultMessage());

        return new ResponseEntity<>(responsePrint, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> myResourceNotFoundException(ResourceNotFoundException e){
        String message= e.getMessage();
        APIResponse apiResponse = new APIResponse(message, false,LocalDateTime.now());
            return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<?> myAPIException(APIException e){
        String message=e.getMessage();
        APIResponse apiResponse=new APIResponse(message,false,LocalDateTime.now());
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }


}
