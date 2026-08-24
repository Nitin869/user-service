package com.socialapp.userservice.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNameAlreadyExistException.class)
    public ErrorResponse handleUserNameAlreadyExistException(UserNameAlreadyExistException ex) {
        return new ErrorResponse(409, ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ErrorResponse handleEmailAlreadyExistException(EmailAlreadyExistException ex){
        return new ErrorResponse(409, ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorResponse handleUsernameNotFoundException(UsernameNotFoundException ex){
        return new ErrorResponse(404, ex.getMessage(), LocalDateTime.now());
    }

}
