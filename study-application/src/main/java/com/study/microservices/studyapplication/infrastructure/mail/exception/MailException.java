package com.study.microservices.studyapplication.infrastructure.mail.exception;

/**
 * <h1>MailException</h1>
 * Supports error during send an email
 *
 * @author
 * @version
 * @since
 */
public class MailException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Pass an error message
     * @param message
     */
    public MailException(String message) {
        super(message);
    }

    /**
     * Pass an error message and the original exception
     * @param message
     * @param cause
     */
    public MailException(String message, Throwable cause) {
        super(message, cause);
    }
}
