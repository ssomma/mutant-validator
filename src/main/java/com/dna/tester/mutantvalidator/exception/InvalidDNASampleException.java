package com.dna.tester.mutantvalidator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDNASampleException extends RuntimeException {
    public InvalidDNASampleException(String exception){
        super(exception);
    }
}
