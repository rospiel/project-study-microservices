package com.study.microservices.studyapplication.infrastructure.mail.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
public class Message {

    private Set<String> recipients;
    private String subject;
    private String body;

    @Singular
    private Map<String, Object> properties;

}
