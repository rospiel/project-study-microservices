package com.study.microservices.studyapplication.infrastructure.storage.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.InputStream;

@Getter
@Setter
@NoArgsConstructor
public class PhotoResponse {

    private InputStream inputStream;
    private String url;

    public PhotoResponse(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public PhotoResponse(String url) {
        this.url = url;
    }
}
