package com.study.microservices.studyapplication.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ResponseStatus(UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException(String message) {
        super(message);
    }

    public UnprocessableEntityException(String message, Long entityId) {
        super(format(message, entityId));
    }

    public UnprocessableEntityException(String message, String entityCode) {
        super(format(message, entityCode));
    }
}
