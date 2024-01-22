package com.mvp.calculator.model.player;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BasketballPlayerGameStats extends PlayerGameStats {
    private int scoredPoints;
    private int rebounds;
    private int assists;
}
