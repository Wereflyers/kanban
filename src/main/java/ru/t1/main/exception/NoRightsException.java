package ru.t1.main.exception;

public class NoRightsException extends RuntimeException {

    public NoRightsException() {
        super("You have no rights to do it");
    }
}