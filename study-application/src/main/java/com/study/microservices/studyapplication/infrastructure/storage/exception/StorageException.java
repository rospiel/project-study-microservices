package com.study.microservices.studyapplication.infrastructure.storage.exception;

/**
 * <h1>StorageException</h1>
 * Supports error during process file in the storage to help someone to determine what happens
 *
 * @author
 * @version
 * @since
 */
public class StorageException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Pass an error message
     * @param message
     */
    public StorageException(String message) {
        super(message);
    }

    /**
     * Pass an error message and the original exception
     * @param message
     * @param cause
     */
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
