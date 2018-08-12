package com.bartosz.pieczara.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class UserAlreadyPresent extends RuntimeException {

    public UserAlreadyPresent(String message) {
        super(message);
    }
}
