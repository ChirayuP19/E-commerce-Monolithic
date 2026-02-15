package com.ecommerce.project.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String field;
    String fieldName;
    Long fieldId;


    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s not foud with %s: %d",resourceName,field,fieldId));
        this.resourceName=resourceName;
        this.field=field;
        this.fieldId=fieldId;
    }

    public ResourceNotFoundException(String resourceName, String field, String fieldName) {
        super(String.format("%s not foud with %s: %d",resourceName,field,fieldName));
        this.resourceName=resourceName;
        this.field=field;
        this.fieldName=fieldName;
    }

    public ResourceNotFoundException() {
    }
}
