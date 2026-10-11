package com.airtribe.learntrack.exception;

/** Thrown when user input is missing, malformed or violates a business rule. */
public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }
}
