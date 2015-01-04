package org.alpha.services;

/**
 * Exception thrown when entity is not found in persistence.
 */
public class EntityNotFoundException extends AppServiceException {

    /**
     * The generated UID.
     */
    private static final long serialVersionUID = -2603444293003421171L;

    /**
     * Creates with the provided message.
     * @param message the error message.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates with the provided message and cause.
     * @param message the error message.
     * @param cause the error cause.
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
