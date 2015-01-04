package org.alpha.services;

/**
 * Exception thrown when entity already exists in persistence.
 */
public class EntityExistsException extends AppServiceException {

    /**
     * The generated UID.
     */
    private static final long serialVersionUID = -8904693867022402819L;

    /**
     * Creates with the provided message.
     * @param message the error message.
     */
    public EntityExistsException(String message) {
        super(message);
    }

    /**
     * Creates with the provided message and cause.
     * @param message the error message.
     * @param cause the error cause.
     */
    public EntityExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
