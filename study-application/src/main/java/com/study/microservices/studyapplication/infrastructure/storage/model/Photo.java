package com.study.microservices.studyapplication.infrastructure.storage.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.InputStream;

@Getter
@Setter
@RequiredArgsConstructor
public class Photo {

    private final String fileName;
    private final String contentType;
    private final InputStream inputStream;
    private final Long size;

}
