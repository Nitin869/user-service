package com.socialapp.userservice.exception;

public class UserNameAlreadyExistException extends RuntimeException {

    public UserNameAlreadyExistException(String message) {
        super(message);
    }
}
