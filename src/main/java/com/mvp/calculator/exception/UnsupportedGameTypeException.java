package com.mvp.calculator.exception;

import com.mvp.calculator.model.GameType;

public class UnsupportedGameTypeException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Game type '%s' is not supported";

    public UnsupportedGameTypeException(GameType type) {
        super(String.format(MESSAGE_TEMPLATE, type.getValue()));
    }
}
