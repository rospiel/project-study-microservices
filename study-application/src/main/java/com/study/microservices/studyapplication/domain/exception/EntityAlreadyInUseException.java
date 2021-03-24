package com.study.microservices.studyapplication.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.CONFLICT;

public class EntityAlreadyInUseException extends RuntimeException {

    public EntityAlreadyInUseException(String message) {
        super(message);
    }

    public EntityAlreadyInUseException(String message, Long entityId) {
        super(format(message, entityId));
    }
}
