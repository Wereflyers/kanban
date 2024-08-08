package ru.kanban.main.exception;

public class AccessDenialException extends RuntimeException {
    public AccessDenialException(String message) {
        super(message);
    }

    public AccessDenialException() {
        super("You have no rights to do it");
    }
}
