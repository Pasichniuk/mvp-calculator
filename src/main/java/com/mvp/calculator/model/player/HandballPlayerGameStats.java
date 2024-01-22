package com.mvp.calculator.model.player;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class HandballPlayerGameStats extends PlayerGameStats {
    private int goalsMade;
    private int goalsReceived;
}
