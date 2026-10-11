package com.airtribe.learntrack.util;

import com.airtribe.learntrack.exception.InvalidInputException;

/** Small helpers for validating and parsing user input. */
public class InputValidator {

    private InputValidator() {
    }

    public static boolean isNotBlank(String text) {
        return text != null && !text.trim().isEmpty();
    }

    /** Very simple email check: something@something.something */
    public static boolean isValidEmail(String email) {
        if (!isNotBlank(email)) {
            return false;
        }
        int at = email.indexOf('@');
        int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }

    public static String requireNotBlank(String text, String fieldName) {
        if (!isNotBlank(text)) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
        return text.trim();
    }

    public static int parseInt(String text, String fieldName) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException | NullPointerException e) {
            throw new InvalidInputException(fieldName + " must be a whole number.");
        }
    }
}
