package com.java_coffee.user_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    private String resouceName;
    private String fieldName;
    private String fieldValue;

    public UserNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with %s: %s", resourceName, fieldName, fieldValue));
        this.resouceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
