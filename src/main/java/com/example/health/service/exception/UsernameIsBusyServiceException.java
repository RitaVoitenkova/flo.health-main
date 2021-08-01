package com.example.health.service.exception;

public class UsernameIsBusyServiceException extends Exception{
    public UsernameIsBusyServiceException(String message) {
        super(message);
    }

    public UsernameIsBusyServiceException() {
        super("Username is busy");
    }
}
