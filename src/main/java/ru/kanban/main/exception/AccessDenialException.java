package ru.kanban.main.exception;

/**
 * The type Access denial exception.
 */
public class AccessDenialException extends RuntimeException {
    /**
     * Instantiates a new Access denial exception.
     *
     * @param message the message
     */
    public AccessDenialException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Access denial exception.
     */
    public AccessDenialException() {
        super("You have no rights to do it");
    }
}
