package com.dna.tester.mutantvalidator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class DBSyncException extends RuntimeException{
    public DBSyncException(String exception){
        super(exception);
    }
}
