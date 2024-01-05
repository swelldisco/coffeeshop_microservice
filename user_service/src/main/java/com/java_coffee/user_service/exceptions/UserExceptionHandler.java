package com.java_coffee.user_service.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException ex, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            ex.getMessage(),
            webRequest.getDescription(false),
            "USER_NOT_FOUND");
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND); 
    }

    @ExceptionHandler(RepositoryEmptyException.class)
    public ResponseEntity<ErrorDetails> handleEmptyRepositoryException(RepositoryEmptyException ex, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            ex.getMessage(),
            webRequest.getDescription(false),
            "REPOSITORY_EMPTY");
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND); 
    }

    @ExceptionHandler(UserNameInUseException.class)
    public ResponseEntity<ErrorDetails> handleEmailInUseException(UserNameInUseException ex, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            ex.getMessage(),
            webRequest.getDescription(false),
            "USER_NAME_ALREADY_IN_USE");
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_ACCEPTABLE); 
    }

    @ExceptionHandler(EmailInUseException.class)
    public ResponseEntity<ErrorDetails> handleUserNameInUseException(UserNameInUseException ex, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            ex.getMessage(),
            webRequest.getDescription(false),
            "USER_NAME_ALREADY_IN_USE");
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_ACCEPTABLE); 
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleUserException(Exception ex, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            ex.getMessage(),
            webRequest.getDescription(false),
            "INTERNAL_APPLICATION_ERROR");
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
        errorList.forEach((e) -> {
            String fieldName = ((FieldError)e).getField();
            String message = e.getDefaultMessage();
            validationErrors.put(fieldName, message);
        });
        return new ResponseEntity<Object>(validationErrors, HttpStatus.BAD_REQUEST);
    }
}
