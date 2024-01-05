package com.java_coffee.user_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class UserNameInUseException extends RuntimeException{
    private String userName;

    public UserNameInUseException(String userName) {
        super(String.format("%s is already in use.", userName));
    }
}
