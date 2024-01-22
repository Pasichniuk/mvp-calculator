package com.mvp.calculator.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GameType {
    BASKETBALL("BASKETBALL"),
    HANDBALL("HANDBALL");

    private final String value;
}
