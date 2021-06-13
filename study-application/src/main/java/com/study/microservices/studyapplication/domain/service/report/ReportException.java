package com.study.microservices.studyapplication.domain.service.report;

public class ReportException extends RuntimeException {

    public ReportException(String message, Throwable cause) {
        super(cause);
    }

    public ReportException(String message) {
        super(message);
    }
}
