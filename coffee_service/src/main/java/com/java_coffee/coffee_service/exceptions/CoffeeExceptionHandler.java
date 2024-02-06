package com.java_coffee.coffee_service.exceptions;

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
public class CoffeeExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(CoffeeNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleCoffeeNotFoundException(CoffeeNotFoundException ex, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            ex.getMessage(),
            webRequest.getDescription(false),
            "COFFEE_NOT_FOUND");
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND); 
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleCartNotFoundException(CartNotFoundException ex, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            ex.getMessage(),
            webRequest.getDescription(false),
            "CART_NOT_FOUND");
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND); 
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleOrderNotFoundException(OrderNotFoundException ex, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            ex.getMessage(),
            webRequest.getDescription(false),
            "ORDER_NOT_FOUND");
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND); 
    }

    @ExceptionHandler(CartMismatchException.class)
    public ResponseEntity<ErrorDetails> handleCartMismatchException(CartMismatchException ex, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            ex.getMessage(),
            webRequest.getDescription(false),
            "CART_MISMATCH");
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
