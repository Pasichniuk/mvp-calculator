package com.mvp.calculator.exception;

public class DirectoryReadFailedException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Failed to read data from directory: %s. Reason: %s";

    public DirectoryReadFailedException(String dirPath, String message) {
        super(String.format(MESSAGE_TEMPLATE, dirPath, message));
    }
}
