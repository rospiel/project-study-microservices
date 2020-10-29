package com.study.microservices.studyapplication.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(CONFLICT)
public class EntityAlreadyInUseException extends RuntimeException {

    public EntityAlreadyInUseException(String message) {
        super(message);
    }
}
