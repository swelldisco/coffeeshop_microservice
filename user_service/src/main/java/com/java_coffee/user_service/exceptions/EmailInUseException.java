package com.java_coffee.user_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class EmailInUseException extends RuntimeException {
    private String emailAddress;

    public EmailInUseException(String emailAddress) {
        super(String.format("%s is already in use.", emailAddress));
    }
}
