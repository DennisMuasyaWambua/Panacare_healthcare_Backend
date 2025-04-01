package com.panacare.panabeans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PanaAppException extends RuntimeException {
    private static final long serialVersionUID = 2685947737768847728L;

    public PanaAppException(String message){
        super(message);
    }

    public PanaAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
