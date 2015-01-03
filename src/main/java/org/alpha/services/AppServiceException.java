package org.alpha.services;

import org.alpha.AppException;


/**
 * The base exception for the services.
 */
public class AppServiceException extends AppException {

    /**
     * The generated UID.
     */
    private static final long serialVersionUID = 6063579584361988400L;

    /**
     * Creates with the provided message.
     * @param message the error message.
     */
    public AppServiceException(String message) {
        super(message);
    }

    /**
     * Creates with the provided message and cause.
     * @param message the error message.
     * @param cause the error cause.
     */
    public AppServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
