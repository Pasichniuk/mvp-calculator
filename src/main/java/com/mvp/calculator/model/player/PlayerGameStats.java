package com.mvp.calculator.model.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public abstract class PlayerGameStats {
    private String playerName;
    private String nickname;
    private String number;
    private String teamName;
}
