package com.mvp.calculator.model;

import com.mvp.calculator.model.player.PlayerGameStats;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GameStats {
    private GameType type;
    private List<PlayerGameStats> playerGameStats;
}
