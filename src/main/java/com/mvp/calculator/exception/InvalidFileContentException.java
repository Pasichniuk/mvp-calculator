package com.mvp.calculator.exception;

public class InvalidFileContentException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Invalid file content. Reason: %s";

    public InvalidFileContentException(String message) {
        super(String.format(MESSAGE_TEMPLATE, message));
    }
}
