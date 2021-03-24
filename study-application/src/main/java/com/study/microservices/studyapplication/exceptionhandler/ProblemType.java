package com.study.microservices.studyapplication.exceptionhandler;

import lombok.Getter;

import static java.lang.String.format;

@Getter
public enum ProblemType {

    ENTITY_ALREADY_IN_USE("Entity already in use", "entity-already-in-use"),
    ENTITY_CANNOT_BE_PROCESSED("Entity cannot be processed", "entity-cannot-be-processed"),
    UNREADABLE_BODY("Unreadable body", "unreadable_body");

    private String title;
    private String uri;

    ProblemType(String title, String path) {
        this.title = title;
        this.uri = format("https://studyapplication.com/%s", path);
    }
}
